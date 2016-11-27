package org.worker.impl.sockscap;

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
	private Target targ = new ImageTarget(Utils.fileUrl("images/proxyButton.png"));
	
	@Override
	public void execute()
	{}
	
	@Override
	public Worker branch()
	{
		System.out.println("Searching for proxy button...");
		targ.setMinScore(0.8);
		ScreenRegion result = AccountCreator.screen.wait(targ, AccountCreator.GENERAL_WAIT_TIME);
		
		if(result != null && clickProxyButton(result))
			return new FillOutProxyInfo();
		
		System.out.println("Could not find proxy button...");
		return null;
	}
	
	private boolean clickProxyButton(ScreenRegion region)
	{
		Mouse mouse = new DesktopMouse();
		mouse.click(region.getCenter());
		
		Target proxyWindow = new ImageTarget(Utils.fileUrl("images/proxyWindow.png"));
		
		return AccountCreator.screen.wait(proxyWindow, AccountCreator.GENERAL_WAIT_TIME) != null;
	}

}
