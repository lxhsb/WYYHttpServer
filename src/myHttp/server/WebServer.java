package myHttp.server;



import myHttp.processor.BlcokingProcessor;
import myHttp.processor.NonBlockingProcessor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by wyy on 17-2-13.
 */
public final class WebServer//不让继承
{
	/*
	想了想，在某些方面，这个类没必要设定成绝对的线程安全。
	 */
	public static final int DEFAULT_PORT = 1208;
	private int port;
	private boolean blocking = true;//记录是否是阻塞io // 默认是阻塞io
	private boolean starting = false;//记录服务器是否正在运行
	private Object obj = new Object();//用来加锁
	private ExecutorService executorService;//在同步阻塞的情况下使用 //默认使用cachedthreadpool
	private Vector<myHttp.processor.NonBlockingProcessor> processors;
	private static Random random = new Random();//用来做负载均衡
	/*
	↓↓↓↓↓↓以下是用来非阻塞所需要的↓↓↓↓↓↓
	 */
	private Selector selector;
	private BlockingQueue<Changes>changesQueue;


	public WebServer()
	{
		this(DEFAULT_PORT);
	}

	public WebServer(int port)
	{
		this.port = port;
	}

	/*
	设置服务器是阻塞式还是非阻塞式
	如果服务器已经启动，则抛出异常
	这里不是线程安全的，因为如果刻意来找麻烦的话是划不来的
	想改成线程安全的则加一下锁就好
	 */
	public void configureBlocking(boolean blockStatus)
			throws Exception//懒得再封装一个新的exception了
	{
		if (starting == true)
			throw new Exception(
					"configure blocking failed! server is alreay started");
		else
			this.blocking = blockStatus;
	}

	/*
	由于在构造的时候并没有初始化好一系列对象，所以要这个函数
	又start自己调用
	 */
	private void initServer() throws Exception
	{
		try
		{
			if (this.blocking == true)
			{
				this.executorService = Executors.newCachedThreadPool();
			}
			else
			{
				this.selector = Selector.open();

				changesQueue = new LinkedBlockingQueue<Changes>();

				ServerSocketChannel serverSocketChannel = ServerSocketChannel
						.open();
				serverSocketChannel.configureBlocking(false);
				serverSocketChannel.socket()
						.bind(new InetSocketAddress(port));//any
				serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

				processors = new Vector<>();
				int cpuNum = Runtime.getRuntime().availableProcessors();
				for (int i = 0; i < 2 * cpuNum; i++)//开2倍应该会比较好吧
				{
					NonBlockingProcessor processor = new NonBlockingProcessor(this);

					processor.run();
					processors.add(processor);
				}
			}
		}
		catch (Exception e)
		{
			throw e;
		}

	}

	public void start() throws Exception
	{
		starting = true;
		initServer();
		if (blocking)
		{
			this.startBlockingServer();
		}
		else
		{
			this.startNonBlockingServer();
		}

	}

	/*
	对于阻塞式的 由每个processer来处理io
	 */
	private void startBlockingServer() throws Exception//阻塞式的
	{

		ServerSocket serverSocket = new ServerSocket(this.port);
		while (true)
		{
			Socket socket = serverSocket.accept();
			this.executorService.execute(new BlcokingProcessor(socket));
		}
	}

	/*
	对于非阻塞式的，由webserver这里的线程来处理io
	 */
	private void startNonBlockingServer() throws Exception//非阻塞式的
	{
		Changes changes = null;
		while (true)
		{
			while((changes = changesQueue.remove())!=null)//这样写应该没错吧 先这样 //这样写在流量大的时候造成死循环？
			{
				SelectionKey key = changes.socketChannel.keyFor(this.selector);
				if(key!=null&&key.isValid())
				{
					key.interestOps(changes.op);
				}
			}
			selector.select();
			Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
			while (keys.hasNext())
			{
				SelectionKey key = keys.next();
				keys.remove();
				if (key.isValid() == false)
				{
					continue;
				}
				if (key.isAcceptable())
				{
					doAccept(key);
				}
				else if (key.isReadable())
				{
					doRead(key);
				}
				else if (key.isWritable())
				{
					doWrite(key);
				}
				else//可能不可能出现别的情况呢？ 我也不知道
				{
					System.err.println("error in key " + key.hashCode()
							+ " with nothing to do");
				}
			}
		}
	}

	private void doAccept(SelectionKey key) throws IOException
	{
		SocketChannel socketChannel = ((ServerSocketChannel) key.channel())
				.accept();
		socketChannel.configureBlocking(false);
		socketChannel.register(selector, SelectionKey.OP_READ);

	}

	private void doRead(SelectionKey key) throws IOException
	{
		SocketChannel socketChannel = (SocketChannel) key.channel();
		ByteBuffer buffer = ByteBuffer.allocate(1024<<2);//暂时不考虑文件上传等一系列问题 //其实这里可以优化一下
		int size = 0;
		try
		{
			size = socketChannel.read(buffer);
			if (size == -1)//出现-1应该就是被关掉了
			{
				socketChannel.close();
				key.cancel();
				return;
			}
		}
		catch (IOException e)//出现异常就关掉
		{
			socketChannel.close();
			key.cancel();
		}
		int sz = processors.size();
		int who = random.nextInt(sz);
		byte bytes[] = new byte[size];
		System.arraycopy(buffer.array(),0,bytes,0,size);
		processors.get(who).addRequest(socketChannel, bytes);//这里之所以要复制下是因为可能并没有读满，避免错误嘛
	}

	private void doWrite(SelectionKey key) throws IOException
	{


	}

	public void send(myHttp.mypackage.ResponsePackage responsePackage)//不是说你要送，我就送，要打申请的
	{



	}

}
/*
这个类是打包封装一下channel的intrestOps的改变
主要是为了防止一些多线程可能会出现的问题。
 */
class Changes
{
	SocketChannel socketChannel;
	int op;
	Changes(SocketChannel socketChannel,int op)
	{
		this.socketChannel = socketChannel;
		this.op = op;
	}
}

