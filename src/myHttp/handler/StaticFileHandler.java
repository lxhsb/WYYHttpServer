package myHttp.handler;

import myHttp.body.HttpMessageBody;
import myHttp.exception.BaseHttpException;
import myHttp.exception.ForbiddenException;
import myHttp.exception.NotFoundException;
import myHttp.request.Request;
import myHttp.response.Response;
import myHttp.response.code.HttpStatusCode;
import myHttp.response.error.ErrorResponse;
import myHttp.util.MIMEMap;
import org.apache.commons.io.FilenameUtils;

import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;

/**
 * Created by wyy on 17-2-14.
 */
public class StaticFileHandler
{
	private Request req;
	private String parentPath;
	private File file;
	public StaticFileHandler(Request req,String parentPath)
	{
		this.req = req ;
		this.parentPath = parentPath;
		this.file = new File(parentPath+req.getPath());
		if(file.isDirectory())
		{
			file = new File(parentPath+req.getPath()+file.separator
			+"index.html");
		}
	}

	public Response handle()throws BaseHttpException,IOException
	{
		//System.out.println(file.getAbsolutePath());
		if(file.exists()==false)
		{
			throw new NotFoundException();
		}
		else if(file.canRead()==false)
		{
			throw new ForbiddenException();
		}
		StringBuilder sb = new StringBuilder();
		FileInputStream fin = new FileInputStream(file);
		InputStreamReader is = new InputStreamReader(fin);
		BufferedReader bin = new BufferedReader(is);
		Response rep = ErrorResponse.getErrorResponse(1000);
		try
		{
			String line = null ;
			while ((line =bin.readLine())!=null)
			{
				sb.append(line).append("\n");//maybe bug here
			}
			String fileSuffix = FilenameUtils.getExtension(file.getName());
			HttpMessageBody httpMessageBody = new HttpMessageBody(MIMEMap.get(fileSuffix),sb.toString());
			rep = new Response(HttpStatusCode.OK,httpMessageBody);
		}catch (IOException e)
		{
			e.printStackTrace();
			throw e ;
		}
		finally
		{
			bin.close();
			is.close();
			fin.close();
		}
		return rep ;




	}
}
