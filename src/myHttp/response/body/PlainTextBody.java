package myHttp.response.body;

/**
 * Created by wyy on 17-2-10.
 */
public class PlainTextBody extends BaseHttpBody
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
