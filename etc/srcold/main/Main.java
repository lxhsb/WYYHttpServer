package main;

import myHttp.exception.FileNotFoundException;
import myHttp.server.HttpServer;

import java.io.IOException;

/**
 * Created by wyy on 17-2-9.
 */
public class Main
{
	public static void main(String args[])
	{
		try
		{
			new HttpServer(1208);
		}
		catch (Exception e)
		{
			e.printStackTrace();

		}

	}
}
