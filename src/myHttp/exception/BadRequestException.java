package myHttp.exception;

/**
 * Created by wyy on 17-2-14.
 */
public class BadRequestException extends BaseHttpException
{
	public static final String BAD_REQUEST_MESSAGE = "Bad Request";
	public BadRequestException()
	{
		super(400,"Bad Request");
	}
	public BadRequestException(String message)
	{
		super(400,message);
	}
}
