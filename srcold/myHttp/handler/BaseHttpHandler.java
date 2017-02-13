package myHttp.handler;

import myHttp.request.Request;
import myHttp.response.Response;

/**
 * Created by wyy on 17-2-11.
 */
public abstract class BaseHttpHandler
{
	public abstract Response doWithRequest(Request req) throws Exception;
}
