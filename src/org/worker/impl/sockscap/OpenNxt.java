package org.worker.impl.sockscap;

import java.awt.image.BufferedImage;

import org.AccountCreator;
import org.Utils;
import org.sikuli.api.ScreenRegion;
import org.worker.Worker;
import org.worker.impl.nxt.CreateAccount;

public class OpenNxt implements Worker
{
	private static final int NXT_WAIT_TIME = 30000;
	private static final int FAILED_WAIT_TIME = 7500;
	
	private static BufferedImage nxtIconTarg = Utils.websiteUrl("nxtIcon.png");
	private static BufferedImage nxtClientTarg = Utils.websiteUrl("nxtClient.png");
	private static BufferedImage failedTarg = Utils.websiteUrl("proxyFailed.png");
	
	@Override
	public void execute()
	{
		ScreenRegion nxtIcon = Utils.waitFor(nxtIconTarg, AccountCreator.GENERAL_WAIT_TIME, .75);
		if(nxtIcon == null)
			return;
		
		AccountCreator.mouse.doubleClick(nxtIcon.getCenter());
	}

	@Override
	public Worker branch()
	{
		ScreenRegion failed = Utils.waitFor(failedTarg, FAILED_WAIT_TIME);
		if(failed != null)
			return null;
		
		ScreenRegion nxtClient = Utils.waitFor(nxtClientTarg, NXT_WAIT_TIME);
		
		return nxtClient != null ? new CreateAccount() : null;
	}

}
