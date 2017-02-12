package myHttp.header;

/**
 * Created by wyy on 17-2-9.
 */
public class Header
{
	private String key;
	private String value;

	public Header(String _key, String _value)
	{
		this.key = _key;
		this.value = _value;
	}

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}

	public String getKey()
	{
		return key;
	}

	public void setKey(String key)
	{
		this.key = key;
	}

	public String toString()
	{
		return this.key + " : " + this.value;
	}
}
