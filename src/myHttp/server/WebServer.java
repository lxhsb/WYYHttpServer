package myHttp.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import javax.xml.ws.handler.Handler;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by wyy on 17-2-13.
 */
public class WebServer
{
	private static final int DEFAULT_PORT = 1208;
	private int port;
	private ExecutorService executorService ;//默认使用cachedthreadpool
	//public static final int DEFALUT_MAX_THREAD_COUNT = 100 ;

	public WebServer()
	{
		this.port = DEFAULT_PORT;
		this.executorService = Executors.newCachedThreadPool();
	}

	public WebServer(int port)
	{
		this.port = port;
		this.executorService = Executors.newCachedThreadPool();
	}

	public void Start() throws Exception
	{
		ServerSocket serverSocket = new ServerSocket(this.port);
		while (true)
		{
			Socket socket = serverSocket.accept();
			this.executorService.execute(new Processor(socket));

		}

	}

}

