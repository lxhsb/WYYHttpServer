package MyHttp.Response.Body;

/**
 * Created by wyy on 17-2-10.
 */
public class PlainTextBody extends HttpBaseBody
{
	public PlainTextBody()
	{
		super();
		setContentType("text/plain");
	}
	public PlainTextBody(String _Content)
	{
		super("text/plain",_Content);
	}

}
