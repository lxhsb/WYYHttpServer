package myHttp.request;

import myHttp.exception.BaseHttpException;
import myHttp.header.Headers;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Vector;

/**
 * Created by wyy on 17-2-9.
 */
public class Request
{
	private HttpMethodEnum httpMethod;
	private String path;
	private String version;
	private Headers headers;
	private Vector<String>bodyLines;

	public Request()
	{
	}
	public HttpMethodEnum getHttpMethod()
	{
		return httpMethod;
	}

	public void setHttpMethod(HttpMethodEnum httpMethod)
	{
		this.httpMethod = httpMethod;
	}

	public String getPath()
	{
		return path;
	}

	public void setPath(String path)
	{
		this.path = path;
	}

	public String getVersion()
	{
		return version;
	}

	public void setVersion(String version)
	{
		this.version = version;
	}

	public Headers getHeaders()
	{
		return headers;
	}

	public void setHeaders(Headers headers)
	{
		this.headers = headers;
	}

	public Vector<String> getBodyLines()
	{
		return bodyLines;
	}

	public void setBodyLines(Vector<String> bodyLines)
	{
		this.bodyLines = bodyLines;
	}

	public Request(BufferedReader input)
			throws IOException, BaseHttpException, Exception
	{//这里可能会出现初始化请求头的各种问题

		String line = null;
		int index = 0;
		/*
		init headers
		 */
		Vector<String> lines = new Vector<>();
		while (input.ready())
		{
			line = input.readLine();
			if (line.length() == 0)
				break;
			//System.out.println(++index + " : " + line);
			++index ;
			System.out.println(index + " : " + line);
			if (index == 1)
			{
				String propers[] = line.split(" ");
				this.httpMethod = HttpMethodEnum.Parse(propers[0]);
				this.path = propers[1];
				this.version = propers[2];//就正常用户来讲，这里是不会有问题的。如果有问题，直接抛出异常
			}
			else
			{
				lines.add(line);
			}
		}
		this.headers = new Headers(lines);
		//System.out.println("init request headers finish ");
		/*
		init headers finish
		going to init request body
		 */
		bodyLines = new Vector<>();
		while (input.ready()){
			line = input.readLine();
			System.out.println(++index + " : " + line);
			bodyLines.add(line);
		}
		//System.out.println("init request body finish ");
		/*
		init request body finish
		 */
		System.out.println("version = "+version+ " path = "+path +" method = "+httpMethod);



	}

}
