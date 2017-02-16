package myHttp.server;

import myHttp.exception.BaseHttpException;
import myHttp.handler.StaticFileHandler;
import myHttp.request.Request;
import myHttp.response.Response;
import myHttp.response.error.ErrorResponse;

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
		Response rep = ErrorResponse.getErrorResponse(1000);//获取未知错误，这里Code是不存在的
		try
		{
			input = socket.getInputStream();
			output = socket.getOutputStream();
			Request req = new Request(input);
			//output.write(req.toString().getBytes());
			/*
			由于暂时只支持单站点，静态文件
			不支持配置
			所以这里就只能先这样实现
			 */
			StaticFileHandler staticFileHandler = new StaticFileHandler(req,".");
			rep = staticFileHandler.handle();


		}
		catch (BaseHttpException e)
		{
			rep = ErrorResponse.getErrorResponse(e.getCode());//根据code来获取相应错误的response
			//output.write(rep.toString().getBytes());

		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
			//	System.out.print(rep.toString());
				output.write(rep.toString().getBytes());
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
