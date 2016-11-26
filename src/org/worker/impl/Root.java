package org.worker.impl;

import java.io.IOException;

import org.AccountCreator;
import org.worker.Worker;

public class Root implements Worker
{
	private static final String SOCKS_CAP = "C:/Users/Freddy/AppData/Local/SocksCap64/SocksCap64.exe";
	
	@Override
	public void execute()
	{
		try
		{
			AccountCreator.socksCap = Runtime.getRuntime().exec(SOCKS_CAP);
			
			try
			{
				Thread.sleep(3000);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public boolean canExecute()
	{
		return true;
	}

	@Override
	public Worker branch()
	{
		return null;
	}

}
