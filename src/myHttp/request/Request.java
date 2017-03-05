package myHttp.request;

import myHttp.body.HttpMessageBody;
import myHttp.exception.BadRequestException;
import myHttp.exception.BaseHttpException;
import myHttp.exception.MethodNotAllowException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

	public Request(InputStream in) throws BaseHttpException
	{

		boolean headersFinish = false;
		headers = new Hashtable<>();
		BufferedReader input = null;
		try
		{
			input = new BufferedReader(new InputStreamReader(in));
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
	public Request(String strRequest) throws BaseHttpException
	{
		// to do
	}

	public Request parse(InputStream in) throws BaseHttpException
	{
		return new Request(in);
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
		sb.append(httpMessageBody.toString()).append("\r\n");
		return sb.toString();
	}

}
