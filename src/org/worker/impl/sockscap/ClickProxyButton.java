package org.worker.impl.sockscap;

import java.awt.image.BufferedImage;

import org.AccountCreator;
import org.Utils;
import org.sikuli.api.ImageTarget;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.Target;
import org.sikuli.api.robot.Mouse;
import org.sikuli.api.robot.desktop.DesktopMouse;
import org.worker.Worker;

public class ClickProxyButton implements Worker
{
	private static BufferedImage targ = Utils.websiteUrl("proxyButton.png");
	
	@Override
	public void execute()
	{}
	
	@Override
	public Worker branch()
	{
		System.out.println("Searching for proxy button...");
		ScreenRegion result = Utils.waitFor(targ, AccountCreator.GENERAL_WAIT_TIME, 0.8);
		
		if(result != null && clickProxyButton(result))
			return new FillOutProxyInfo();
		
		System.out.println("Could not find proxy button...");
		return null;
	}
	
	private boolean clickProxyButton(ScreenRegion region)
	{
		Mouse mouse = new DesktopMouse();
		mouse.click(region.getCenter());
		
		Target proxyWindow = new ImageTarget(Utils.websiteUrl("proxyWindow.png"));
		
		return AccountCreator.screen.wait(proxyWindow, AccountCreator.GENERAL_WAIT_TIME) != null;
	}

}
