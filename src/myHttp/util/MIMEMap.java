package myHttp.util;

import java.util.Hashtable;
import java.util.Map;

/**
 * Created by wyy on 17-2-11.
 */
public class MIMEMap
{
	public final static String HTML_TYPE = "text/html; charset=utf-8";
	public final static String TEXT_TYPE = "text/plain";
	public final static String GIF_TYPE = "image/gif";
	public final static String JPG_TYPE = "image/jpeg";
	public final static String JPEG_TYPE = "image/jpeg";
	public final static String PNG_TYPE = "image/png";
	public final static String DOWNLOAD_TYPE = "application/download";

	private static Map<String, String> mp = new Hashtable<String, String>()//这个是线程安全的，省不少事;
	{
		{
			put("html", HTML_TYPE);
			put("txt", TEXT_TYPE);
			put("gif", GIF_TYPE);
			put("jpg", JPG_TYPE);
			put("jpeg", JPEG_TYPE);
			put("png", PNG_TYPE);
		}
	};

	public static void put(String FileType, String Type) throws Exception
	{
		put(FileType, Type);
	}

	public static String get(String FileType)//如果是unknown的文件类型 则返回下载的类型 ！
	{
		if (FileType != null)
		{
			FileType = FileType.toLowerCase();
		}
		if (mp.containsKey(FileType))
		{
			return mp.get(FileType);
		}
		else
		{
			return DOWNLOAD_TYPE;
		}

	}

}
