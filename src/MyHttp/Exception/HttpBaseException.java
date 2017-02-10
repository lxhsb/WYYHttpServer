package MyHttp.Exception;

/**
 * Created by wyy on 17-2-9.
 */
public class HttpBaseException extends Exception
{
	private int code;
	private String message;

	HttpBaseException(int _code, String _message)
	{
		super(_message);
		this.message = _message;
		this.code = _code;
	}

	public int getCode()
	{
		return code;
	}

	public void setCode(int code)
	{
		this.code = code;
	}

	@Override

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}
}
