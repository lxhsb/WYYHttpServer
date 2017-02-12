package myHttp.server;

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

		Socket socket = serverSocket.accept();

		BufferedReader input = new BufferedReader(
				new InputStreamReader(socket.getInputStream()));


		BufferedWriter output = new BufferedWriter(
				new OutputStreamWriter(socket.getOutputStream()));


		try
		{
			Request req = new Request(input);
			PlainTextBody plainTextBody = new PlainTextBody("hello world");
			Response rep = new Response(Response.DEFAULTVERSION, HttpCode.OK,new Headers(),plainTextBody);
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
