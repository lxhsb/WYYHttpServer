package myHttp.response;

import myHttp.body.HttpMessageBody;
import myHttp.response.code.HttpStatusCode;

import java.util.Map;

/**
 * Created by wyy on 17-2-14.
 */
public class Response
{
	public static final String DEFAULT_VERSION = "HTTP/1.1";
	private String version ;
	private Map<String,String>headers;
	private HttpStatusCode httpStatusCode;
	private HttpMessageBody httpMessageBody;

	public Response(String version,HttpStatusCode httpStatusCode,Map<String,String>headers,HttpMessageBody httpMessageBody)
	{
		this.version = version;
		this.httpStatusCode = httpStatusCode;
		this.headers = headers;
		this.httpMessageBody = httpMessageBody;
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

}
