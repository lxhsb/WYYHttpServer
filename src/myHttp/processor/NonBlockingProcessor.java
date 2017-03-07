package myHttp.processor;

import myHttp.exception.BaseHttpException;
import myHttp.handler.StaticFileHandler;
import myHttp.request.Request;
import myHttp.response.Response;
import myHttp.response.error.ErrorResponse;

import myHttp.server.WebServer;
import myHttp.mypackage.RequestPackage;
import myHttp.mypackage.ResponsePackage;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by wyy on 17-3-5.
 */
public class NonBlockingProcessor implements Runnable
{
	private BlockingQueue<RequestPackage> requestQueue = new LinkedBlockingDeque<>();
	private WebServer server;

	public NonBlockingProcessor(WebServer server)
	{
		this.server = server;
	}

	public void addRequest(SocketChannel client, byte[] bytes)
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
			requestQueue.put(new RequestPackage(client,req));//用put不用add和offer
		}
		catch (BaseHttpException e)
		{
			//e.printStackTrace();
			Response rep = ErrorResponse.getErrorResponse(e.getCode());
			this.server.send(new ResponsePackage(client,rep));

		}
		catch (Exception e)
		{
			//e.printStackTrace();
			Response rep = ErrorResponse.BAD_REQUEST_RESPONSE;
			this.server.send(new ResponsePackage(client,rep));
		}

	}

	@Override public void run()
	{
		Response response = null ;
		RequestPackage requestPackage = null ;
		ResponsePackage responsePackage = null;

		while (true)
		{
			response = null;
			requestPackage = null;
			responsePackage = null;
			try
			{
				//Request req = requestQueue.take();//会阻塞的
				requestPackage = requestQueue.take();
				StaticFileHandler staticFileHandler = new StaticFileHandler(requestPackage.getRequest(),
						"./wwwroot");
				response = staticFileHandler.handle();
			}
			catch (BaseHttpException e )
			{
				response = ErrorResponse.getErrorResponse(e.getCode());
			}
			catch (InterruptedException e )
			{
				e.printStackTrace();
			}
			catch (IOException e )
			{
				e.printStackTrace();
			}
			finally
			{
				responsePackage = new ResponsePackage(requestPackage.getSocketChannel(),response);
				this.server.send(responsePackage);
			}
		}

	}
}

