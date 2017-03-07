package myHttp.request;

import myHttp.body.HttpMessageBody;
import myHttp.exception.BadRequestException;
import myHttp.exception.BaseHttpException;
import myHttp.exception.MethodNotAllowException;

import java.io.*;
import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;

/**
 * Created by wyy on 17-2-14.
 */
public class Request
{
	private HttpMethod httpMethod;
	private String path;
	private String version;
	private Map<String, String> headers;
	private HttpMessageBody httpMessageBody;
	/*
	这里的构造函数嵌套的比较多
	好多最后都没有关，待改
	虽说可能不会造成内存泄露，但是应该还是影响性能的
	 */

	public Request(InputStream in) throws BaseHttpException
	{
		this(new BufferedReader(new InputStreamReader(in)));
	}
	public Request(String strRequest) throws BaseHttpException
	{
		System.out.println("in request "+strRequest);
		// to do
		//this(new BufferedReader(new StringReader(strRequest)));
		BufferedReader input = new BufferedReader(new StringReader(strRequest));
		headers = new Hashtable<>();
		try
		{
			String[] firstLine = null;
			String line = null;
			while (input.ready())//暂时先只处理get请求
			{
				line = input.readLine();
				System.out.println(line);
				if (line.length() == 0)
				{
					break;
				}
				else if (firstLine == null)
				{
					firstLine = line.split(" ");
					if (firstLine.length != 3)
					{
						throw new BadRequestException();

					}
					this.httpMethod = HttpMethod.get(firstLine[0]);
					this.path = firstLine[1];
					this.version = firstLine[2];
				}
				else
				{
					String[] tmp = line.split(":");
					headers.put(tmp[0], tmp[1]);
				}
			}
		}
		catch (BaseHttpException e)
		{
			throw e;
		}
		catch (IOException e)
		{
			throw new BadRequestException();
		}
	}
	public Request(BufferedReader input) throws BaseHttpException
	{
		//boolean headersFinish = false;
		headers = new Hashtable<>();
		try
		{
			String[] firstLine = null;
			String line = null;
			while ((line = input.readLine()) != null)//暂时先只处理get请求
			{
				//System.out.println(line);
				if (line.length() == 0)
				{
					break;
				}
				else if (firstLine == null)
				{
					firstLine = line.split(" ");
					if (firstLine.length != 3)
					{
						throw new BadRequestException();

					}
					this.httpMethod = HttpMethod.get(firstLine[0]);
					this.path = firstLine[1];
					this.version = firstLine[2];
				}
				else
				{
					String[] tmp = line.split(":");
					headers.put(tmp[0], tmp[1]);
				}
			}
		}
		catch (BaseHttpException e)
		{
			throw e;
		}
		catch (IOException e)
		{
			throw new BadRequestException();
		}
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

	public Map<String, String> getHeaders()
	{
		return headers;
	}

	public void setHeaders(Map<String, String> headers)
	{
		this.headers = headers;
	}

	public HttpMessageBody getHttpMessageBody()
	{
		return httpMessageBody;
	}

	public void setHttpMessageBody(HttpMessageBody httpMessageBody)
	{
		this.httpMessageBody = httpMessageBody;
	}
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(httpMethod + " " + path + " " + version).append("\r\n");
		for (String key : headers.keySet())
		{
			sb.append(key + ":" + headers.get(key)).append("\r\n");
		}
		sb.append("\r\n");
		if(httpMessageBody!=null)
		{
			sb.append(httpMessageBody.toString());
			sb.append("\r\n");
		}

		return sb.toString();
	}

}
