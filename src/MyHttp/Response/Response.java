package MyHttp.Response;

import MyHttp.Header.Header;
import MyHttp.Header.Headers;
import MyHttp.Response.Body.HttpBaseBody;
import MyHttp.Response.Code.HttpCode;

/**
 * Created by wyy on 17-2-10.
 */
public class Response
{
	private Headers headers;
	private HttpBaseBody httpBaseBody;
	private String version;
	private HttpCode code ;

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

	public HttpBaseBody getHttpBaseBody()
	{
		return httpBaseBody;
	}

	public void setHttpBaseBody(HttpBaseBody httpBaseBody)
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
		httpBaseBody = new HttpBaseBody();
		version = "";
		code = new HttpCode(0,"");

	}
	public Response(String _version,HttpCode _code ,Headers _headers,HttpBaseBody _httpBaseBody)
	{
		this.version = _version;
		this.headers = _headers;
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
