package myHttp.response;

import myHttp.header.Header;
import myHttp.header.Headers;
import myHttp.response.body.BaseHttpBody;

/**
 * Created by wyy on 17-2-10.
 */
public class Response
{
	private Headers headers;
	private BaseHttpBody httpBaseBody;
	private String version;
	private HttpCode code ;

	public static final String DEFAULT_VERSION = "HTTP/1.1";

	public HttpCode getCode()
	{
		return code;
	}

	public void setCode(HttpCode code)
	{
		this.code = code;
	}

	public Headers getHeaders()
	{
		return headers;
	}

	public void setHeaders(Headers headers)
	{
		this.headers = headers;
	}

	public BaseHttpBody getHttpBaseBody()
	{
		return httpBaseBody;
	}

	public void setHttpBaseBody(BaseHttpBody httpBaseBody)
	{
		this.httpBaseBody = httpBaseBody;
	}

	public String getVersion()
	{
		return version;
	}

	public void setVersion(String version)
	{
		this.version = version;
	}
	public Response(){
		headers = new Headers();
		httpBaseBody = new BaseHttpBody();
		version = DEFAULT_VERSION;
		code = new HttpCode(0,"");

	}
	public Response(String _version,HttpCode _code ,Headers _headers,BaseHttpBody _httpBaseBody)
	{
		this.version = _version;
		this.headers = _headers;
		this.httpBaseBody = _httpBaseBody;
		this.code = _code;
		headers.put("Content-Type",new Header("Content-Type",httpBaseBody.getContentType()));
		headers.put("Content-Length",new Header("Content-Length",String.valueOf(httpBaseBody.getContentLength())));

	}
	public Response(String _version,HttpCode _code ,BaseHttpBody _httpBaseBody)
	{
		this.version = _version;
		this.headers = new Headers();
		this.httpBaseBody = _httpBaseBody;
		this.code = _code;
		headers.put("Content-Type",new Header("Content-Type",httpBaseBody.getContentType()));
		headers.put("Content-Length",new Header("Content-Length",String.valueOf(httpBaseBody.getContentLength())));

	}

	public String toString()
	{
		String ans = version +" "+code.getCode()+" "+ code.getMessage() +"\r\n"+headers.toString()+"\r\n"+httpBaseBody.toString();

		return ans ;

	}

}
