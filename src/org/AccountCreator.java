package org;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import org.proxy.GimmeProxy;
import org.sikuli.api.DesktopScreenRegion;
import org.sikuli.api.ImageTarget;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.Target;
import org.worker.Worker;
import org.worker.Workers;

public class AccountCreator
{
	private Worker currentWorker = Workers.ROOT;

	public static String email, displayName, password;
	public static Process socksCap, nxt;
	
	public AccountCreator()
	{
		email = null;
		displayName = null;
		password = null;
		socksCap = null;
		nxt = null;
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
		
	}
	
	public void killProcesses()
	{
		if(socksCap != null)
			socksCap.destroy();
		
		if(nxt != null)
			nxt.destroy();
	}
	
	//junk test method -- will be removed
	public void test()
	{
		GimmeProxy testProxy = new GimmeProxy();
		if(!testProxy.grabInfo())
		{
			System.out.println("Failed to grab proxy info...");
			return;
		}
		
		System.out.println("Proxy IP: " + testProxy.getIp() + ", Port: " + testProxy.getPort());
		ScreenRegion test = new DesktopScreenRegion();
		System.out.println("width: " + test.getBounds().getWidth() + ", height: " + test.getBounds().getHeight());
		try
		{
			Target imageTarget = new ImageTarget(new File("images/Vikings.jpg").toURI().toURL());
			ScreenRegion result = test.wait(imageTarget, 1000);
			if(result == null)
				System.out.println("Image not found");
			else
				System.out.println("Image found");
		}
		catch(MalformedURLException e)
		{
			e.printStackTrace();
		}
	}
}
