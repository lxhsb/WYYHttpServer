package pkg;

import myHttp.response.Response;

import java.nio.channels.SocketChannel;

/**
 * Created by wyy on 17-3-6.
 */ /*
用来包装一下
 */
public class ResponsePackage
{
	private SocketChannel socketChannel;
	private Response response;
	public ResponsePackage(SocketChannel socketChannel,Response response)
	{
		this.response = response;
		this.socketChannel = socketChannel;
	}

	public SocketChannel getSocketChannel()
	{
		return socketChannel;
	}

	public void setSocketChannel(SocketChannel socketChannel)
	{
		this.socketChannel = socketChannel;
	}

	public Response getResponse()
	{
		return response;
	}

	public void setResponse(Response response)
	{
		this.response = response;
	}
}
