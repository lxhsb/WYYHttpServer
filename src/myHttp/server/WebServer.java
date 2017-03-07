package myHttp.server;

import myHttp.mypackage.ResponsePackage;
import myHttp.processor.BlcokingProcessor;
import myHttp.processor.NonBlockingProcessor;
import myHttp.response.Response;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.*;

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
	/*
	↓↓↓↓↓↓以下是用来非阻塞所需要的↓↓↓↓↓↓
	 */
	private Selector selector;
	private BlockingQueue<Changes> changesQueue;
	private Vector<NonBlockingProcessor> processors;
	private static Random random = new Random();//用来做负载均衡
	private Map<SocketChannel, BlockingQueue<Response>> socketChannelResponseMap;//用于存放将要送往socketchannel的数据//这里还有优化的空间，不过我暂时想不到

	/*
	构造函数
	 */
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
				System.out.println("init selector finish");

				changesQueue = new LinkedBlockingQueue<Changes>();
				System.out.println("init changesqueue finish");

				socketChannelResponseMap = new ConcurrentHashMap<>();
				System.out.println("init map finish");

				ServerSocketChannel serverSocketChannel = ServerSocketChannel
						.open();
				serverSocketChannel.configureBlocking(false);
				serverSocketChannel.socket()
						.bind(new InetSocketAddress(port));//any
				serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
				System.out.println("init serversocketchannel finish");

				processors = new Vector<>();
				System.out.println("init processors victor finish");
				int cpuNum = Runtime.getRuntime().availableProcessors();
				System.out.println(cpuNum);
				for (int i = 0; i < 2 * cpuNum; i++)//开2倍应该会比较好吧
				{
					NonBlockingProcessor processor = new NonBlockingProcessor(
							this);
					new Thread(processor).start();

					processors.add(processor);
					System.out.println("init processor "+i+ " finish");
				}
			}
		}
		catch (Exception e)
		{
			throw e;
		}

	}
	/*
	启动
	 */

	public void start() throws Exception
	{
		System.out.println("start"+(blocking?"阻塞":"非阻塞"));
		starting = true;
		initServer();
		System.out.println("init complete");
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
			System.out.println("start to handle changes ");
			while (changesQueue.isEmpty() == false)//这样写应该没错吧 先这样 //这样写在流量大的时候造成死循环？
			{
				changes = changesQueue.remove();
				SelectionKey key = changes.socketChannel.keyFor(this.selector);
				if (key != null && key.isValid())
				{
					System.out.println(key.channel().hashCode()+" change to write mode");
					key.interestOps(changes.op);
				}
			}
			System.out.println("changes handle done ");
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
	private void doCloseChannel(SelectionKey key)//如果要关掉一个channel ，使用这个//有一个问题，已经断开的连接并且没有任何动作该怎么处理
	{
		SocketChannel socketChannel = (SocketChannel) key.channel();
		if(socketChannelResponseMap.containsKey(socketChannel))
			socketChannelResponseMap.remove(socketChannel);
		try
		{
			socketChannel.close();
		}
		catch (Exception e )
		{
			e.printStackTrace();
			//不知道要干啥
		}
		key.cancel();
	}

	private void doAccept(SelectionKey key) throws IOException
	{

		SocketChannel socketChannel = ((ServerSocketChannel) key.channel())
				.accept();
		socketChannel.configureBlocking(false);
		socketChannel.register(selector, SelectionKey.OP_READ);
		System.out.println("new connection from "+ socketChannel.socket().getRemoteSocketAddress());
	}
	private void doRead(SelectionKey key) throws IOException
	{
		SocketChannel socketChannel = (SocketChannel) key.channel();
		ByteBuffer buffer = ByteBuffer
				.allocate(1024 << 2);//暂时不考虑文件上传等一系列问题 //其实这里可以优化一下
		int size = 0;
		try
		{
			size = socketChannel.read(buffer);
			if (size == -1)//出现-1应该就是被关掉了
			{
				doCloseChannel(key);
				return;
			}
		}
		catch (IOException e)//出现异常就关掉
		{
			doCloseChannel(key);
		}
		int sz = processors.size();
		int who = random.nextInt(sz);
		byte bytes[] = new byte[size];
		System.arraycopy(buffer.array(), 0, bytes, 0, size);
		System.out.println("processor "+who+"is going to handle ");
		System.out.println(new String(bytes));
		processors.get(who)
				.addRequest(socketChannel, bytes);//这里之所以要复制下是因为可能并没有读满，避免错误嘛
	}

	private void doWrite(SelectionKey key) throws IOException
	{

		SocketChannel socketChannel = (SocketChannel)key.channel();
		System.out.println(socketChannel.hashCode()+"do write ");
		BlockingQueue<Response> queue = socketChannelResponseMap.get(socketChannel);
		if(queue == null)
			return;
		while(!queue.isEmpty())
		{
			Response response = queue.element();
			if(response==null)
			{
				break;
			}
			System.out.println("get response "+ response.toString());
			ByteBuffer byteBuffer = ByteBuffer.wrap(response.toString().getBytes("UTF8"));
			socketChannel.write(byteBuffer);
			if(byteBuffer.remaining()>0)
			{
				break;
			}
			queue.remove();

		}
		if(queue.isEmpty())
		{
			try
			{
				changesQueue.put(new Changes(socketChannel,SelectionKey.OP_READ));
			}
			catch (InterruptedException e )
			{
				e.printStackTrace();
			}

		}



	}

	public void send(ResponsePackage responsePackage)//不是说你要送，我就送，要打申请的
	{
		SocketChannel socketChannel = responsePackage.getSocketChannel();
		Response response = responsePackage.getResponse();
		BlockingQueue<Response> queue = this.socketChannelResponseMap.get(socketChannel);
		if (queue == null)
		{
			queue = new LinkedBlockingDeque<Response>();
			this.socketChannelResponseMap.put(socketChannel, queue);
		}
		try
		{
			queue.put(response);
			System.out.println(socketChannel.hashCode()+" put success");
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();

		}
		Changes changes = new Changes(responsePackage.getSocketChannel(),
				SelectionKey.OP_WRITE);
		try
		{
			changesQueue.put(changes);
		}
		catch (Exception e )
		{
			e.printStackTrace();
		}

		selector.wakeup();

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

	Changes(SocketChannel socketChannel, int op)
	{
		this.socketChannel = socketChannel;
		this.op = op;
	}
}

