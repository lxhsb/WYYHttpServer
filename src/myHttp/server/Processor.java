package myHttp.server;

import myHttp.exception.BaseHttpException;
import myHttp.request.Request;

import java.io.*;
import java.net.Socket;

/**
 * Created by wyy on 17-2-13.
 */
public class Processor implements Runnable
{
	private Socket socket;

	public Processor(Socket socket)
	{
		this.socket = socket;

	}

	@Override public void run()
	{
		InputStream input = null;
		OutputStream output = null;
		try
		{
			input = socket.getInputStream();
			output = socket.getOutputStream();
			Request req = new Request(input);
			//output.write(req.toString().getBytes());

		}
		catch (BaseHttpException e)
		{

		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				output.flush();
				input.close();
				output.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();

			}

		}

	}
}
