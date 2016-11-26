package org.proxy;
import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

public class GimmeProxy
{
	private static final String URL = "http://www.gimmeproxy.com/api/getProxy?protocol=socks5";
	
	private String ip;
	private String port;
	
	public boolean grabInfo()
	{
		try
		{
			JSONObject json = JSONUtils.readJsonFromUrl(URL);
			ip = json.getString("ip");
			port = json.getString("port");
			return true;
		}
		catch(IOException | JSONException e)
		{
			e.printStackTrace();
		}
		
		return false;
	}
	
	//getters
	public String getIp()
	{
		return ip;
	}
	
	public String getPort()
	{
		return port;
	}
}
