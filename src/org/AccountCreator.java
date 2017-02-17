package org;
import java.io.IOException;

import org.db.AccountRecorder;
import org.proxy.ProxyLoader;
import org.sikuli.api.DesktopScreenRegion;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.robot.Mouse;
import org.sikuli.api.robot.desktop.DesktopKeyboard;
import org.sikuli.api.robot.desktop.DesktopMouse;
import org.worker.Worker;
import org.worker.impl.sockscap.ClickProxyButton;

public class AccountCreator
{
	public static final int GENERAL_WAIT_TIME = 2000;
	
	public static ScreenRegion screen = new DesktopScreenRegion();
	public static Mouse mouse = new DesktopMouse();
	public static DesktopKeyboard keyboard = new DesktopKeyboard();
	
	public static String email, password, proxyIp, proxyPort, proxyUser, proxyPass;
	public static Process socksCap;
	public static ProxyLoader proxies = new ProxyLoader();
	
	private Worker currentWorker;
	
	public AccountCreator()
	{
		currentWorker = new ClickProxyButton();
		
		email = null;
		password = null;
		socksCap = null;
		proxyIp = null;
		proxyPort = null;
		proxyUser = null;
		proxyPass = null;
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
	
	public void storeLocally()
	{
		new AccountRecorder().recordAccount(email, password, proxyIp, proxyPort, proxyUser, proxyPass);
	}
	
	public void killProcesses()
	{
		if(socksCap != null)
			socksCap.destroy();
		
		try
		{
			Runtime.getRuntime().exec("taskkill /F /IM RuneScape.exe");
			Runtime.getRuntime().exec("taskkill /F /IM GamePanel.exe");
			Runtime.getRuntime().exec("taskkill /F /IM rs2client.exe");
			Runtime.getRuntime().exec("taskkill /F /IM chrome.exe");
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}
