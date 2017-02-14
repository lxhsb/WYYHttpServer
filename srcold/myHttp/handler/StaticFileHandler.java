package myHttp.handler;

import myHttp.request.Request;
import myHttp.response.Response;
import myHttp.response.body.PlainTextBody;
import myHttp.response.body.StaticFileBody;
import myHttp.util.FileReader;

import java.io.File;

/**
 * Created by wyy on 17-2-12.
 */
public class StaticFileHandler extends BaseHttpHandler//考虑了线程安全
{

	private final String ParentPath;//体重解析的目录名
	public StaticFileHandler(){
		ParentPath = ".";
	}
	public StaticFileHandler(String _parentpath){
		this.ParentPath = _parentpath;
	}

	public String getParentPath()
	{
		return ParentPath;
	}
	@Override
	public Response doWithRequest(Request req) throws Exception
	{
		Response rep= new Response(Response.DEFAULT_VERSION,HttpCode.OK,new PlainTextBody("服务器好像发生了点逻辑错误?"));;
		String path = ParentPath+req.getPath();
		File file = new File(path);
		if(file.isDirectory()){//是目录
			file = new File(path+file.separator+"index.html");
		}
		try
		{
			StaticFileBody staticFileBody = new StaticFileBody(file);
			rep = new Response(Response.DEFAULT_VERSION,HttpCode.OK,staticFileBody);

		}
		catch (Exception e ){
			throw e;
		}

			return rep;



	}
}
