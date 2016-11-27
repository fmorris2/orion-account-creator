package org;
import java.io.IOException;

import org.sikuli.api.DesktopScreenRegion;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.robot.Mouse;
import org.sikuli.api.robot.desktop.DesktopKeyboard;
import org.sikuli.api.robot.desktop.DesktopMouse;
import org.worker.Worker;
import org.worker.impl.sockscap.OpenSocksCap;

public class AccountCreator
{
	public static final int GENERAL_WAIT_TIME = 2000;

	public static ScreenRegion screen = new DesktopScreenRegion();
	public static Mouse mouse = new DesktopMouse();
	public static DesktopKeyboard keyboard = new DesktopKeyboard();
	
	public static String email, password;
	public static Process socksCap;
	
	private Worker currentWorker;
	
	public AccountCreator()
	{
		currentWorker = new OpenSocksCap();
		
		email = null;
		password = null;
		socksCap = null;
	}
	
	public boolean create()
	{
		while(currentWorker != null)
		{	
			if(email != null)
				return true;
			
			currentWorker.execute();
			currentWorker = currentWorker.branch();
		}
		
		System.out.println("Unknown phase reached.... returning false");
		return false;
	}
	
	public boolean report()
	{
		return false;
	}
	
	public void storeLocally()
	{
		new AccountRecorder().recordAccount(email, password);
	}
	
	public void killProcesses()
	{
		if(socksCap != null)
			socksCap.destroy();
		
		try
		{
			Runtime.getRuntime().exec("taskkill /F /IM RuneScape.exe");
			Runtime.getRuntime().exec("taskkill /F /IM rs2client.exe");
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}
