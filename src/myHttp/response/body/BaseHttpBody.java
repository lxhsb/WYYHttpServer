package myHttp.response.body;

/**
 * Created by wyy on 17-2-10.
 */
public class BaseHttpBody
{
	private String ContentType;
	private long ContentLength;
	private String Content;

	public String getContentType()
	{
		return ContentType;
	}

	public void setContentType(String contentType)
	{
		ContentType = contentType;
	}

	public long getContentLength()
	{
		return ContentLength;
	}

	public void setContentLength(long contentLength)
	{
		ContentLength = contentLength;
	}

	public String getContent()
	{
		return Content;
	}

	public void setContent(String content)
	{
		Content = content;
	}

	public BaseHttpBody()
	{
	}

	public BaseHttpBody(String _ContentType, String _Content)
	{
		this.Content = _Content;
		this.ContentType = _ContentType;
		this.ContentLength = this.Content.getBytes().length;//不知道这里默认的是不是UTF8 坑待填
	}
	public String toString()
	{
		return this.Content.toString();
	}

}
