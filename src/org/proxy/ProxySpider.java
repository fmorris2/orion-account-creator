package org.proxy;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONPointerException;

public class ProxySpider extends ProxyRotator
{
	private final String URL;
	
	public ProxySpider()
	{
		URL = "http://proxy-spider.com/api/proxies.json?" +
				"api_key=03-2bfab8b6091b948d6e0ba55075c3672" +
				"&limit=1" +
				"&type=anonymous,elite" +
				"&protocols=socks5";
	}
	
	@Override
	public boolean grabInfo()
	{
		try
		{
			JSONObject json = JSONUtils.readJsonFromUrl(URL);
			ip = json.query("/data/proxies/0/ip").toString();
			port = json.query("/data/proxies/0/port").toString();
			return true;
		}
		catch(JSONPointerException e)
		{
			System.out.println("Proxy spider returned no proxies");
		}
		catch(IOException | JSONException e)
		{
			e.printStackTrace();
		}
		
		return false;
	}

}
