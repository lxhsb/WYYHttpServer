package myHttp.response.body;

import myHttp.exception.FileNotFoundException;
import myHttp.util.*;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by wyy on 17-2-11.
 */
public class StaticFileBody extends BaseHttpBody
{
	private File File;
	private String FileExtension;
	private final FileReader fileReader = new FileReader();

	public StaticFileBody(File file) throws IOException, FileNotFoundException
	{
		setFile(file);
	}

	public File getFile()
	{
		return File;
	}

	public void setFile(File file) throws IOException, FileNotFoundException
	{
		try
		{
			this.File = file;
			this.FileExtension = FilenameUtils.getExtension(file.getName());
			setContentType(MIMEMap.get(FileExtension));
			setContent(fileReader.ReadFile(file));
		}
		catch (java.io.FileNotFoundException e)
		{
			throw new FileNotFoundException();
		}
		catch (IOException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw e;
		}
	}

	public String getFileExtension()
	{
		return FileExtension;
	}

}
