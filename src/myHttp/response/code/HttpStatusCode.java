package myHttp.response.code;

/**
 * Created by wyy on 17-2-14.
 */
public class HttpStatusCode
{
	private int code;
	private String message;

	public int getCode()
	{
		return code;
	}

	public void setCode(int code)
	{
		this.code = code;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public HttpStatusCode(int _code, String _message)
	{
		this.code = _code;
		this.message = _message;
	}

	public String toString()
	{
		return this.code + " " + this.message;
	}

	public static final HttpStatusCode OK = new HttpStatusCode(200, "OK");
	public static final HttpStatusCode FILE_NOT_FOUND = new HttpStatusCode(404,
			"Not Found");
	public static final HttpStatusCode METHOD_NOT_ALLOW = new HttpStatusCode(
			405, "Method Not Allow");
	public static final HttpStatusCode BAD_REQUEST = new HttpStatusCode(400,
			"Bad Request");

}
