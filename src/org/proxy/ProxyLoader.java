package org.proxy;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

public class ProxyLoader
{
	private static final String DIR = "/org/proxies.txt";
	
	private Queue<Subnet> subnets = new LinkedList<>(); 
	private Subnet currentSubnet;
	
	public ProxyLoader()
	{
		loadProxies();
	}
	
	public Proxy getProxy()
	{
		for(int i = 0; i < subnets.size(); i++)
		{
			Subnet s = subnets.poll();
			subnets.add(s);
			currentSubnet = s;
			
			Proxy p = s.getProxy();
			if(p != null)
				return p;
		}
		
		ProxyRotator proxy = new CoolProxy();
		
		while(true)
		{
			try
			{
				if(!proxy.grabInfo())
				{
					if(proxy instanceof ProxySpider)
					{
						System.out.println("ProxySpider proxies exhausted.... Falling back to CoolProxies");
						proxy = new CoolProxy();
					}
				}
				
				if(proxy.getIP() != null)
					break;
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
		return proxy;
	}
	
	private void loadProxies()
	{
		int totalProxies = 0;
		
		try
		(
			InputStreamReader fr = new InputStreamReader(this.getClass().getResourceAsStream(DIR));
			BufferedReader br = new BufferedReader(fr);
		)
		{
			String line;
			while((line = br.readLine()) != null)
			{
				String[] parts = line.split(":");
				if(parts.length > 2)
					addProxy(new Proxy(parts[0], parts[1], parts[2], parts[3]));
				else
					addProxy(new Proxy(parts[0], parts[1], null, null));
				
				totalProxies++;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		System.out.println("Loaded " + totalProxies + " proxies on " + subnets.size() + " different subnets");
	}
	
	private void addProxy(Proxy p)
	{
		boolean success = false;
		for(Subnet s : subnets)
			if(s.addProxy(p))
				success = true;
		
		if(!success)
		{
			subnets.add(new Subnet());
			addProxy(p);
		}
	}
	
	public Subnet getCurrentSubnet()
	{
		return currentSubnet;
	}
}
