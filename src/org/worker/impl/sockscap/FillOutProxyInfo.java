package org.worker.impl.sockscap;

import org.AccountCreator;
import org.Utils;
import org.proxy.Proxy;
import org.sikuli.api.DefaultScreenLocation;
import org.sikuli.api.ImageTarget;
import org.sikuli.api.Relative;
import org.sikuli.api.ScreenLocation;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.Target;
import org.worker.Worker;

public class FillOutProxyInfo implements Worker
{	
	private Target targ = new ImageTarget(Utils.fileUrl("images/proxyRowIcon.png"));
	//private Target portXTarg = new ImageTarget(Utils.fileUrl("images/proxyPortX.png"));
	private Target proxySaveTarg = new ImageTarget(Utils.fileUrl("images/proxySaveButton.png"));
	
	private boolean success;
	
	@Override
	public void execute()
	{
		System.out.println("Fill out proxy info");
		targ.setMinScore(0.8);
		ScreenRegion rowIcon = AccountCreator.screen.wait(targ, AccountCreator.GENERAL_WAIT_TIME);
		//ScreenRegion proxyPortX = AccountCreator.screen.wait(portXTarg, AccountCreator.GENERAL_WAIT_TIME);
		ScreenRegion saveButton = AccountCreator.screen.wait(proxySaveTarg, AccountCreator.GENERAL_WAIT_TIME);
		
		if(rowIcon == null /*|| proxyPortX == null */|| saveButton == null)
			return;	
		
		/*
		GimmeProxy proxy = new GimmeProxy();
		if(!proxy.grabInfo())
			return;
		*/
		
		Proxy proxy = AccountCreator.proxies.getProxy();
		
		//IP
		ScreenRegion ipBox = Relative.to(rowIcon).right(50).getScreenRegion();
		AccountCreator.mouse.click(ipBox.getCenter()); //click on it
		AccountCreator.mouse.doubleClick(ipBox.getCenter()); //get box in input mode
		AccountCreator.mouse.doubleClick(ipBox.getCenter()); //highlight all text
		AccountCreator.keyboard.type(proxy.getIP());
		
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
		AccountCreator.mouse.click(saveButton.getCenter());
		
		success = true;
	}

	@Override
	public Worker branch()
	{
		return success ? new OpenNxt() : null;
	}

}
