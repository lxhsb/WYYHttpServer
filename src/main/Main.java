package main;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import myHttp.server.WebServer;
//import myHttp.server.myhandler;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by wyy on 17-2-13.
 */
public class Main
{
	public static void main(String[] args)
	{

		WebServer webServer = new WebServer();
		try
		{
			webServer.Start();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}


	}

}


