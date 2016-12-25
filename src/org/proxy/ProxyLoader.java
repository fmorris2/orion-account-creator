package org.proxy;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.Queue;

public class ProxyLoader
{
	private static final String DIR = "proxies.txt";
	
	private Queue<Proxy> proxies = new LinkedList<>();
	
	public ProxyLoader()
	{
		loadProxies();
	}
	
	public Proxy getProxy()
	{
		Proxy p = proxies.poll();
		proxies.add(p);
		return p;
	}
	
	private void loadProxies()
	{
		try
		(
			FileReader fr = new FileReader(DIR);
			BufferedReader br = new BufferedReader(fr);
		)
		{
			String line;
			while((line = br.readLine()) != null)
			{
				String[] parts = line.split(":");
				proxies.add(new Proxy(parts[0], parts[1]));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
