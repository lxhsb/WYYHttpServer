package myHttp.server;

import myHttp.request.Request;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
	//	private ServerSocketChannel serverSocketChannel;
	private BlockingQueue<Request> requestQueue;//每接受处理好一个request就放在这里面

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
				ServerSocketChannel serverSocketChannel = ServerSocketChannel
						.open();
				serverSocketChannel.configureBlocking(false);
				serverSocketChannel.socket()
						.bind(new InetSocketAddress(port));//any
				serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
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
		if (blocking == true)
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
			this.executorService.execute(new Processor(socket));
		}
	}

	/*
	对于非阻塞式的，由webserver这里的线程来处理io
	 */
	private void startNonBlockingServer() throws Exception//非阻塞式的
	{
		while(true)
		{



		}

	}

}

