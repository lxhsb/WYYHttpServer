package myHttp.header;

import java.util.HashMap;
import java.util.Vector;

/**
 * Created by wyy on 17-2-9.
 */
public class Headers extends HashMap<String, Header>
{

	public Headers()
	{
	}

	public Headers(Vector<String> lines)
	{//比较喜欢使用vector 因为首先是线程安全的 其次就可能是从C++哪里用习惯了
		for (String line : lines)
		{
			String props[] = line.split(":");
			StringBuilder sb = new StringBuilder();
			for (int i = 1; i < props.length; i++)
			{
				sb.append(props[i] + " ");
			}
			Header header = new Header(props[0].trim(), sb.toString().trim());
			this.put(header.getKey(), header);
		}
	}

	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		for (Header header : this.values())
		{
			sb.append(header.toString());
			sb.append("\r\n");//换行
		}
		return sb.toString();

	}

}
