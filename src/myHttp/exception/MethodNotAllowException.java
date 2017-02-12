package myHttp.exception;

/**
 * Created by wyy on 17-2-9.
 */
public class MethodNotAllowException extends BaseHttpException
{
	public MethodNotAllowException()
	{
		super(405, "Method Not Allow");
	}
}
