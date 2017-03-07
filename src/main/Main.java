package main;

import myHttp.server.WebServer;
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
			webServer.configureBlocking(false);
			webServer.start();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}


	}

}



