package MyHttp.Exception;

/**
 * Created by wyy on 17-2-9.
 */
public class MethodNotAllowException extends HttpBaseException
{
	public MethodNotAllowException()
	{
		super(405, "Method Not Allow");
	}
}
