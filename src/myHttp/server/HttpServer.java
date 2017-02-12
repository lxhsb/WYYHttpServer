package myHttp.server;

import myHttp.exception.*;
import myHttp.handler.StaticFileHandler;
import myHttp.header.Headers;
import myHttp.request.Request;
import myHttp.response.body.PlainTextBody;
import myHttp.response.code.HttpCode;
import myHttp.response.Response;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by wyy on 17-2-9.
 */
public class HttpServer
{
	public HttpServer(int port) throws IOException
	{
		ServerSocket serverSocket = new ServerSocket(port);
		while (true)
		{
			Socket socket = serverSocket.accept();

			BufferedReader input = new BufferedReader(
					new InputStreamReader(socket.getInputStream()));

			BufferedWriter output = new BufferedWriter(
					new OutputStreamWriter(socket.getOutputStream()));

			Response rep = null;
			StaticFileHandler staticFileHandler = new StaticFileHandler();
			try
			{
				Request req = new Request(input);
				rep = staticFileHandler.doWithRequest(req);
				System.out.print(rep.getHeaders().toString());
			}
			catch (MethodNotAllowException e)
			{
				rep = new Response(Response.DEFAULT_VERSION,
						HttpCode.METHODNOTALLOW,
						new PlainTextBody("Method Not Allow"));
			}
			catch (myHttp.exception.FileNotFoundException e)
			{
				//System.out.println("发现问题,文件不存在");
				rep = new Response(Response.DEFAULT_VERSION,
						HttpCode.FILENOTFOUND,
						new PlainTextBody("File Not Found "));
			}
			catch (IOException e)
			{
				rep = new Response(Response.DEFAULT_VERSION, HttpCode.OK,
						new PlainTextBody(e.toString()));

			}
			catch (Exception e)
			{
				e.printStackTrace();
				rep = new Response(Response.DEFAULT_VERSION, HttpCode.OK,
						new PlainTextBody(e.toString()));
			}
			finally
			{
				try
				{
					output.write(rep.toString());
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
}
