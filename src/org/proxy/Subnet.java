package org.proxy;

import java.util.LinkedList;
import java.util.Queue;

public class Subnet
{
	private static final int NUM_PERIODS = 3;
	private static final int COOLDOWN_TIME = (60 * 1000) * 15;
	
	private Queue<Proxy> proxies;
	private String subnet;
	private long cooldownStart;
	
	public Subnet()
	{
		proxies = new LinkedList<>();
	}
	
	public Proxy getProxy()
	{
		if(System.currentTimeMillis() - cooldownStart > COOLDOWN_TIME)
		{
			Proxy p = proxies.poll();
			proxies.add(p);
			return p;
		}
		
		return null;
	}
	
	public boolean addProxy(Proxy p)
	{
		String[] parts = p.getIP().split("\\.");
		String proxySubnet = "";
		for(int i = 0; i < NUM_PERIODS; i++)
			proxySubnet += parts[i];
		
		subnet = subnet == null ? proxySubnet : subnet;
		
		if(proxySubnet.equals(subnet))
		{
			proxies.add(p);
			return true;
		}
		
		return false;
	}
	
	public void cooldown()
	{
		cooldownStart = System.currentTimeMillis();
	}
	
	public String toString()
	{
		return subnet;
	}
}
