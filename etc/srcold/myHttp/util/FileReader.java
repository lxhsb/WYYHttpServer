package myHttp.util;

import java.io.*;

/**
 * Created by wyy on 17-2-11.
 */
public class FileReader
{
	public String ReadFile(File file) throws IOException
	{
		System.out.print(file.getAbsolutePath());
		try
		{
		FileInputStream fin = new FileInputStream(file);

		InputStreamReader in = new InputStreamReader(fin);
		BufferedReader input = new BufferedReader(in);
		StringBuilder sb = new StringBuilder();

			String line = null;
			while (input.ready())
			{
				line = input.readLine();
				sb.append(line);
			}
			//System.out.println(sb.toString());
			input.close();
			in.close();
			fin.close();
			return sb.toString();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
			throw e;
		}
		catch (IOException e)
		{
			e.printStackTrace();
			throw e;
		}




	}
}
