package myHttp.exception;

/**
 * Created by wyy on 17-2-14.
 */
public class BaseHttpException extends Exception
{
	private int code ;
	private String message ;
	public BaseHttpException(int code ,String message)
	{
		super(message);
		this.code = code ;
		this.message = message;

	}

	public int getCode()
	{
		return code;
	}

	public void setCode(int code)
	{
		this.code = code;
	}

	@Override public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}
}
