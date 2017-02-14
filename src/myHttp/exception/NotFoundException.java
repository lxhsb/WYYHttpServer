package myHttp.exception;

/**
 * Created by wyy on 17-2-14.
 */
public class NotFoundException extends BaseHttpException
{
	public static final String NOT_FOUND_MESSAGE = "Not Found";

	public NotFoundException( String message)
	{
		super(404, message);
	}
	public NotFoundException()
	{
		super(404,"Not Found");
	}
}
