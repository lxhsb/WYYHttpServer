package myHttp.server;

import java.net.ServerSocket;
import java.net.Socket;

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
