package myHttp.body;

import myHttp.exception.BadRequestException;
import myHttp.exception.ForbiddenException;
import myHttp.exception.MethodNotAllowException;
import myHttp.exception.NotFoundException;
import myHttp.util.MIMEMap;

import java.io.IOException;

/**
 * Created by wyy on 17-2-14.
 */
public class HttpMessageBody
{

	private String contentType;
	private long contentLength;
	private String content;
	public HttpMessageBody(String contentType, String content)
	{
		this.content = content;
		try
		{
			this.contentLength = content.getBytes("UTF-8").length;

		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		this.contentType = contentType;
	}

	public String getContentType()
	{
		return contentType;
	}

	public long getContentLength()
	{
		return contentLength;
	}

	public String getContent()
	{
		return content;
	}
	public String toString()
	{
		return content;
	}
	public static final HttpMessageBody BAD_REQUEST_MESSAGE_BODY = new HttpMessageBody(
			MIMEMap.HTML_TYPE, BadRequestException.BAD_REQUEST_MESSAGE);
	public static final HttpMessageBody NOT_FOUND_MESSAGE_BODY = new HttpMessageBody(MIMEMap.HTML_TYPE,
			NotFoundException.NOT_FOUND_MESSAGE);
	public static final HttpMessageBody METHOD_NOT_ALLOW_MESSAGE_BODY = new HttpMessageBody(MIMEMap.HTML_TYPE,
			MethodNotAllowException.METHOD_NOT_ALLOW_MESSAGE);
	public static final HttpMessageBody FORBIDDEN_MESSAGE_BODY = new HttpMessageBody(MIMEMap.HTML_TYPE,
			ForbiddenException.FORBIDDEN_MESSAGE);
}
