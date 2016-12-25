package org.proxy;

public class Proxy
{
	private String ip;
	private String port;
	
	public Proxy(String ip, String port)
	{
		this.ip = ip;
		this.port = port;
	}
	
	public String getIP()
	{
		return ip;
	}
	
	public String getPort()
	{
		return port;
	}
}
