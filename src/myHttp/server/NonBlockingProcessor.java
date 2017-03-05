package myHttp.server;

import myHttp.exception.BaseHttpException;
import myHttp.request.Request;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by wyy on 17-3-5.
 */
public class NonBlockingProcessor implements Runnable
{
	private BlockingQueue<Request> requestQueue = new LinkedBlockingDeque<>();


	public void addRequest(SocketChannel client ,byte[] bytes)
	{
		/*
		先将bytes转为string 再传给request的构造函数
		可能会出现badrequest等情况
		如果出现的话直接就在这里返回response
		 */
		try
		{
			String str = new String(bytes);
			Request req = new Request(str);
			requestQueue.put(req);//用put不用add和offer
		}
		catch (BaseHttpException e )
		{

		}
		catch (Exception e )
		{

		}

	}

	@Override public void run()
	{

	}
}
