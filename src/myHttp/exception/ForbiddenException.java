package myHttp.exception;

/**
 * Created by wyy on 17-2-16.
 */
public class ForbiddenException extends BaseHttpException
{
	public static final String FORBIDDEN_MESSAGE = "FORBIDDEN";
	public ForbiddenException()
	{
		super(403,FORBIDDEN_MESSAGE);
	}
	public ForbiddenException(String message)
	{
		super(403,message);
	}
}
