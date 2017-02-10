package MyHttp.Server;

import MyHttp.Header.Headers;
import MyHttp.Request.Request;
import MyHttp.Response.Body.PlainTextBody;
import MyHttp.Response.Code.HttpCode;
import MyHttp.Response.Response;

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
		Socket socket = serverSocket.accept();
		BufferedReader input = new BufferedReader(
				new InputStreamReader(socket.getInputStream()));
		BufferedWriter output = new BufferedWriter(
				new OutputStreamWriter(socket.getOutputStream()));
		try
		{
			Request req = new Request(input);
			PlainTextBody plainTextBody = new PlainTextBody("hello world");
			Response rep = new Response("HTTP/1.1", HttpCode.OK,new Headers(),plainTextBody);
			output.write(rep.toString());
			output.flush();
			input.close();
			output.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}



	}
}
