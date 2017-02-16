package myHttp.response.error;

import myHttp.body.HttpMessageBody;
import myHttp.response.Response;
import myHttp.response.code.HttpStatusCode;
import myHttp.util.MIMEMap;

import java.util.Hashtable;
import java.util.Map;

/**
 * Created by wyy on 17-2-14.
 */
public class ErrorResponse
{
	public static final Response BAD_REQUEST_RESPONSE = new Response(
			Response.DEFAULT_VERSION, HttpStatusCode.BAD_REQUEST,
			new Hashtable<String, String>()
			{
				{
					put("Content-Type", MIMEMap.HTML_TYPE);
					put("Content-Length", Long.toString(
							HttpMessageBody.BAD_REQUEST_MESSAGE_BODY
									.getContentLength()));
				}
			}, HttpMessageBody.BAD_REQUEST_MESSAGE_BODY);

	public static final Response NOT_FOUND_RESPONSE = new Response(
			Response.DEFAULT_VERSION, HttpStatusCode.FILE_NOT_FOUND,
			new Hashtable<String, String>()
			{
				{
					put("Content-Type", MIMEMap.HTML_TYPE);
					put("Content-Length", Long.toString(
							HttpMessageBody.NOT_FOUND_MESSAGE_BODY
									.getContentLength()));
				}
			}, HttpMessageBody.NOT_FOUND_MESSAGE_BODY);

	public static final Response METHOD_NOT_ALLOW_RESPONSE = new Response(
			Response.DEFAULT_VERSION, HttpStatusCode.METHOD_NOT_ALLOW,
			new Hashtable<String, String>()
			{
				{
					put("Content-Type", MIMEMap.HTML_TYPE);
					put("Content-Length", Long.toString(
							HttpMessageBody.METHOD_NOT_ALLOW_MESSAGE_BODY
									.getContentLength()));
				}
			}, HttpMessageBody.METHOD_NOT_ALLOW_MESSAGE_BODY);
	public static final Response FORBIDDEN_RESPONSE = new Response(
			Response.DEFAULT_VERSION, HttpStatusCode.FORBIDDEN,
			new Hashtable<String, String>()
			{
				{
					put("Content-Type", MIMEMap.HTML_TYPE);
					put("Content-Length", Long.toString(
							HttpMessageBody.FORBIDDEN_MESSAGE_BODY
									.getContentLength()));
				}
			}, HttpMessageBody.FORBIDDEN_MESSAGE_BODY);
	/*
	本来想在这里把暂未处理的code给常量化，结果发现事情并不简单。。。。
	对于一个未知（暂时还没实现）的错误，起码要正确的返回他的Code
	常量化就没法实现这个功能了
	 */
	//public static final Response UNKNOWN_ERROR_RESPONSE = new Response()

	private static Map<Integer, Response> mp = new Hashtable<Integer, Response>()
	{{
		put(404, NOT_FOUND_RESPONSE);
		put(400, BAD_REQUEST_RESPONSE);
		put(405, NOT_FOUND_RESPONSE);
		put(403,FORBIDDEN_RESPONSE);
	}};

	public static  Response getErrorResponse(int code)
	{
		if (mp.containsKey(code))
		{
			return mp.get(code);
		}
		else
		{
			return new Response(Response.DEFAULT_VERSION,
					new HttpStatusCode(code, "server unknown"),
					new Hashtable<String, String>()
					{
						{
							put("Content-Type", MIMEMap.HTML_TYPE);
							put("Content-Length", Long.toString(
									"server has some unknown error"
											.getBytes().length));

						}
					}, new HttpMessageBody(MIMEMap.HTML_TYPE,
					"Server has some unknown error"));

		}
	}

}
