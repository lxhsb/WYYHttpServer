package myHttp.exception;

/**
 * Created by wyy on 17-2-14.
 */
public class MethodNotAllowException extends BaseHttpException
{
	public static final String METHOD_NOT_ALLOW_MESSAGE = "Message Not Allow";
	public MethodNotAllowException()
	{
		super(405,"Message Not Allow");
	}

	public MethodNotAllowException(String message)
	{
		super(405,message);
	}

}
