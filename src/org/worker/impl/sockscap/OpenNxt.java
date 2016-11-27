package org.worker.impl.sockscap;

import org.AccountCreator;
import org.Utils;
import org.sikuli.api.ImageTarget;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.Target;
import org.worker.Worker;
import org.worker.impl.nxt.CreateAccount;

public class OpenNxt implements Worker
{
	private static final int NXT_WAIT_TIME = 10000;
	
	private Target nxtIconTarg = new ImageTarget(Utils.fileUrl("images/nxtIcon.png"));
	private Target nxtClientTarg = new ImageTarget(Utils.fileUrl("images/nxtClient.png"));
	
	@Override
	public void execute()
	{
		ScreenRegion nxtIcon = AccountCreator.screen.wait(nxtIconTarg, AccountCreator.GENERAL_WAIT_TIME);
		if(nxtIcon == null)
			return;
		
		AccountCreator.mouse.doubleClick(nxtIcon.getCenter());
	}

	@Override
	public Worker branch()
	{
		ScreenRegion nxtClient = AccountCreator.screen.wait(nxtClientTarg, NXT_WAIT_TIME);
		
		return nxtClient != null ? new CreateAccount() : null;
	}

}
