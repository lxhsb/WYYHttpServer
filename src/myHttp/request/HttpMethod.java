package myHttp.request;

import myHttp.exception.MethodNotAllowException;

/**
 * Created by wyy on 17-2-14.
 */
public enum  HttpMethod
{
	GET,POST;
	public static HttpMethod get(String Method) throws MethodNotAllowException
	{
		switch (Method.toUpperCase())
		{
		case "GET":
			return GET;
			/*
			暂时不支持post
			 */
		//case "POST":
			//return POST;
		}
		throw new MethodNotAllowException();

	}

}
