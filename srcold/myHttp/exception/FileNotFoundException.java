package myHttp.exception;

/**
 * Created by wyy on 17-2-11.
 */
public class FileNotFoundException extends BaseHttpException
{
	public FileNotFoundException(){
		super(404,"File Not Found");
	}
}
