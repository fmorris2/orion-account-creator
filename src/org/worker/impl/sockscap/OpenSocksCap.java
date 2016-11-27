package org.worker.impl.sockscap;

import java.io.IOException;

import org.AccountCreator;
import org.worker.Worker;

public class OpenSocksCap implements Worker
{
	private static final String SOCKS_CAP = System.getProperty("user.home") + "\\AppData\\Local\\SocksCap64\\SocksCap64.exe";
	private static final int MAX_ATTEMPTS = 3;
	
	private int timesAttempted;
	
	@Override
	public void execute()
	{
		try
		{
			System.out.println("Opening SocksCap");
			AccountCreator.socksCap = Runtime.getRuntime().exec(SOCKS_CAP);
			timesAttempted++;
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public Worker branch()
	{
		if(timesAttempted >= MAX_ATTEMPTS)
			return null;
			
		if(AccountCreator.socksCap.isAlive())
		{
			timesAttempted = 0;
			return new ClickProxyButton();
		}
		
		return this;
	}

}
