package myHttp.response.code;

/**
 * Created by wyy on 17-2-10.
 */

public class HttpCode
{
	private int code ;
	private String message ;

	public int getCode()
	{
		return code;
	}

	public void setCode(int code)
	{
		this.code = code;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public HttpCode(int _code ,String _message){
		this.code = _code;
		this.message = _message;
	}
	public static final HttpCode OK = new HttpCode(200,"OK");
	public static final HttpCode FILENOTFOUND = new HttpCode(404,"File Not Found");
	public static final HttpCode METHODNOTALLOW =new HttpCode(405,"Method Not Allow");


}
