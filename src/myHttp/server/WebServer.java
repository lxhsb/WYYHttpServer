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

/**
 * Created by wyy on 17-2-13.
 */
public class WebServer
{
	private static final int DEFAULT_PORT = 1208;
	private int port;

	public WebServer()
	{
		this.port = DEFAULT_PORT;
	}

	public WebServer(int port)
	{
		this.port = port;
	}

	public void Start() throws Exception
	{
		ServerSocket serverSocket = new ServerSocket(this.port);
		while (true)
		{
			Socket socket = serverSocket.accept();
			//System.out.print(socket.hashCode());
			new Processor(socket).run();
		}

	}

}

