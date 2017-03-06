package myHttp.mypackage;

import myHttp.request.Request;

import java.nio.channels.SocketChannel;

/**
 * Created by wyy on 17-3-6.
 */
/*
用来包装一下
 */
public class RequestPackage
{
	private SocketChannel socketChannel;
	private Request request;
	public RequestPackage(SocketChannel socketChannel , Request request)
	{
		this.request = request;
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

	public Request getRequest()
	{
		return request;
	}

	public void setRequest(Request request)
	{
		this.request = request;
	}
}
