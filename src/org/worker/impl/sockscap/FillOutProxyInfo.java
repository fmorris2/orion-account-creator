package org.worker.impl.sockscap;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.net.InetAddress;

import org.AccountCreator;
import org.Utils;
import org.proxy.Proxy;
import org.sikuli.api.Relative;
import org.sikuli.api.ScreenRegion;
import org.worker.Worker;

public class FillOutProxyInfo implements Worker
{	
	private static BufferedImage targ = Utils.websiteUrl("localSocks.png");
	//private Target portXTarg = new ImageTarget(Utils.fileUrl("images/proxyPortX.png"));
	private static BufferedImage proxySaveTarg = Utils.websiteUrl("proxySaveButton.png");
	
	private boolean success;
	
	@Override
	public void execute()
	{
		System.out.println("Fill out proxy info");
		ScreenRegion rowIcon = Utils.waitFor(targ, AccountCreator.GENERAL_WAIT_TIME, 0.8);
		//ScreenRegion proxyPortX = AccountCreator.screen.wait(portXTarg, AccountCreator.GENERAL_WAIT_TIME);
		ScreenRegion saveButton = Utils.waitFor(proxySaveTarg, AccountCreator.GENERAL_WAIT_TIME);
		
		if(rowIcon == null /*|| proxyPortX == null */|| saveButton == null)
			return;	
		
		/*
		GimmeProxy proxy = new GimmeProxy();
		if(!proxy.grabInfo())
			return;
		*/
		
		Proxy proxy = AccountCreator.proxies.getProxy();
		while(proxy == null)
		{
			try
			{
				System.out.println("All subnets are on cooldown... Attempting again in 5 seconds");
				Thread.sleep(5000);
			}
			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
			
			proxy = AccountCreator.proxies.getProxy();
		}
		
		AccountCreator.proxyIp = proxy.getIP();
		AccountCreator.proxyPort = proxy.getPort();
		AccountCreator.proxyUser = proxy.getUser();
		AccountCreator.proxyPass = proxy.getPass();

		
		//IP
		try
		{
			ScreenRegion ipBox = Relative.to(rowIcon).below(25).getScreenRegion();
			String resolvedIp = InetAddress.getByName(proxy.getIP()).getHostAddress();
			clickAndType(ipBox, resolvedIp);
			
			ScreenRegion portBox = Relative.to(ipBox).right(130).getScreenRegion();
			clickAndType(portBox, proxy.getPort());
			
			ScreenRegion userBox = Relative.to(portBox).right(100).getScreenRegion();
			clickAndType(userBox, proxy.getUser() == null ? " " : proxy.getUser());
			
			ScreenRegion passBox = Relative.to(userBox).right(100).getScreenRegion();
			clickAndType(passBox, proxy.getPass() == null ? " " : proxy.getPass());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		//PORT
		/*
		ScreenLocation portLoc = new DefaultScreenLocation(AccountCreator.screen.getScreen(), 
				proxyPortX.getCenter().getX(), rowIcon.getCenter().getY());
		AccountCreator.mouse.click(portLoc);
		AccountCreator.mouse.doubleClick(portLoc);
		AccountCreator.mouse.doubleClick(portLoc);
		AccountCreator.keyboard.type(proxy.getPort());
		*/
		
		//SAVE BUTTON
		Utils.click(saveButton.getCenter());
		
		success = true;
	}
	
	private void clickAndType(ScreenRegion r, String s)
	{
		Utils.click(r.getCenter()); //click on it
		AccountCreator.mouse.doubleClick(r.getCenter()); //get box in input mode
		AccountCreator.keyboard.keyDown(KeyEvent.VK_CONTROL);
		AccountCreator.keyboard.keyDown(KeyEvent.VK_A);
		AccountCreator.keyboard.keyUp(KeyEvent.VK_A);
		AccountCreator.keyboard.keyUp(KeyEvent.VK_CONTROL);
		AccountCreator.keyboard.type(s);
		AccountCreator.keyboard.keyDown(KeyEvent.VK_ENTER);
		AccountCreator.keyboard.keyUp(KeyEvent.VK_ENTER);
	}

	@Override
	public Worker branch()
	{
		return success ? new OpenNxt() : null;
	}

}
