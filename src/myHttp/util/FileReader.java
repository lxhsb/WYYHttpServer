package myHttp.util;

import java.io.*;

/**
 * Created by wyy on 17-2-11.
 */
public class FileReader
{
	public String ReadFile(File file) throws IOException
	{
		FileInputStream fin = null;
		InputStreamReader in = null;
		BufferedReader input = null;
		StringBuilder sb;
		try
		{
			sb = new StringBuilder();
			fin = new FileInputStream(file);
			in = new InputStreamReader(fin);
			input = new BufferedReader(in);
			String line = null;
			while (input.ready())
			{
				line = input.readLine();
				sb.append(line);
			}
			//System.out.println(sb.toString());

		}
		catch (IOException e)
		{
			throw e;
		}
		finally
		{
			fin.close();
			in.close();
			input.close();

		}

		return sb.toString();

	}
}
