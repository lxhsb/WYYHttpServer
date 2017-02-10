package MyHttp.Request;

import MyHttp.Exception.HttpBaseException;
import MyHttp.Header.Headers;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Vector;

/**
 * Created by wyy on 17-2-9.
 */
public class Request
{
	private HttpMethod httpMethod;
	private String path;
	private String version;
	private Headers headers;
	private Vector<String>bodyLines;

	public Request()
	{
	}

	public HttpMethod getHttpMethod()
	{
		return httpMethod;
	}

	public void setHttpMethod(HttpMethod httpMethod)
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
			throws IOException, HttpBaseException, Exception
	{//这里可能会出现初始化请求头的各种问题

		String line = null;
		int index = 0;
		/*
		init headers
		 */
		Vector<String> lines = new Vector<>();
		while ((line = input.readLine()) != null)
		{
			System.out.println(index + " : " + line);
			if (index == 0)
			{
				String propers[] = line.split(" ");
				this.httpMethod = HttpMethod.Parse(propers[0]);
				this.path = propers[1];
				this.version = propers[2];//就正常用户来讲，这里是不会有问题的。如果有问题，直接抛出异常
			}
			else
			{
				if (line.length() == 0)
					break;
				else
				{
					lines.add(line);
				}
			}
			index++;
		}
		this.headers = new Headers(lines);
		System.out.println("init request headers finish ");
		/*
		init headers finish
		going to init request body
		 */
		bodyLines = new Vector<>();

		line = input.readLine();
		while (line!=null&&line.length()!=0)
		{
			bodyLines.add(line);
			line = input.readLine();
		}
		System.out.println("init request body finish ");
		/*
		init request body finish
		 */



	}

}
