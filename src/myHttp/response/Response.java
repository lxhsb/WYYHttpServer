package myHttp.response;

import myHttp.body.HttpMessageBody;
import myHttp.response.code.HttpStatusCode;

import java.nio.channels.SocketChannel;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by wyy on 17-2-14.
 */
public class Response
{
	public static final String DEFAULT_VERSION = "HTTP/1.1";
	private String version;
	private Map<String, String> headers;
	private HttpStatusCode httpStatusCode;
	private HttpMessageBody httpMessageBody;

	public Response(String version, HttpStatusCode httpStatusCode,
			Map<String, String> headers, HttpMessageBody httpMessageBody)
	{
		this.version = version;
		this.httpStatusCode = httpStatusCode;
		this.headers = headers;
		this.httpMessageBody = httpMessageBody;
	}

	public Response(HttpStatusCode httpStatusCode,
			HttpMessageBody httpMessageBody)
	{
		this.version = DEFAULT_VERSION;
		this.httpStatusCode = httpStatusCode;
		this.httpMessageBody = httpMessageBody;
		this.headers = new Hashtable<>();
		this.headers.put("Content-type", httpMessageBody.getContentType());
		this.headers.put("Content-Length",
				Long.toString(httpMessageBody.getContentLength()));
	}

	public HttpStatusCode getHttpStatusCode()
	{
		return httpStatusCode;
	}

	public void setHttpStatusCode(HttpStatusCode httpStatusCode)
	{
		this.httpStatusCode = httpStatusCode;
	}

	public HttpMessageBody getHttpMessageBody()
	{
		return httpMessageBody;
	}

	public void setHttpMessageBody(HttpMessageBody httpMessageBody)
	{
		this.httpMessageBody = httpMessageBody;
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

	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(this.version + " " + httpStatusCode.toString())
				.append("\r\n");
		for (String key : headers.keySet())
		{
			sb.append(key + " : " + headers.get(key)).append("\r\n");
		}
		sb.append("Connection:keep-alive").append("\r\n");//暂时这样写 always keep alive
		sb.append("\r\n");
		sb.append(httpMessageBody.toString());
		return sb.toString();
//		String ans = ;
//		StringBuilder sb = new StringBuilder();
//		sb.append("HTTP/1.1 200 OK").append("\r\n");
//	//	String hello = "hello world" + socketChannel.hashCode();
//	//	String hello = "你好世界" + socketChannel.hashCode();
//		String hello = httpMessageBody.toString()+socketChannel.hashCode();
//		sb.append("Connection:keep-alive").append("\r\n");
//		sb.append("Content-Length:" + hello.length())
//				.append("\r\n");
//		sb.append("Content-Type:text/html").append("\r\n");
//		sb.append("\r\n");
//		sb.append(hello);
//		System.out.println(sb.toString());
//		return sb.toString();

	}

}
