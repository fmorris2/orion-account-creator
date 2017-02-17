package org.proxy;

public class Proxy
{
	protected String ip;
	protected String port;
	protected String user;
	protected String pass;
	
	public Proxy(String ip, String port, String user, String pass)
	{
		this.ip = ip;
		this.port = port;
		this.user = user;
		this.pass = pass;
	}
	
	public Proxy()
	{
		
	}
	
	public String getUser()
	{
		return user;
	}
	
	public String getPass()
	{
		return pass;
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
