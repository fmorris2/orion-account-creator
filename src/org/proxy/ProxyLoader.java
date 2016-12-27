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
		return null;
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
				addProxy(new Proxy(parts[0], parts[1]));
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
