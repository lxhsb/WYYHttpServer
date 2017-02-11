package MyHttp.Exception;

/**
 * Created by wyy on 17-2-11.
 */
public class FileNotFoundException extends HttpBaseException
{
	public FileNotFoundException(){
		super(404,"File Not Found");
	}
}
