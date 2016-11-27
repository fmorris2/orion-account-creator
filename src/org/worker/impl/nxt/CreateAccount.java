package org.worker.impl.nxt;

import org.AccountCreator;
import org.Utils;
import org.sikuli.api.ImageTarget;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.Target;
import org.worker.Worker;

public class CreateAccount implements Worker
{
	private static final int LOADING_WAIT_TIME = 15000;
	private static final int DONE_BUTTON_WAIT_TIME = 5000;
	
	private Target createAccButton = new ImageTarget(Utils.fileUrl("images/createAccount.png"));
	private Target doneButton = new ImageTarget(Utils.fileUrl("images/doneButton.png"));
	
	private boolean success;
	
	@Override
	public void execute()
	{
		ScreenRegion accButton = AccountCreator.screen.wait(createAccButton, LOADING_WAIT_TIME);
		if(accButton == null)
			return;
		
		AccountCreator.mouse.click(accButton.getCenter());
		
		ScreenRegion done = AccountCreator.screen.wait(doneButton, DONE_BUTTON_WAIT_TIME);
		if(done == null)
			return;
		
		AccountCreator.mouse.click(done.getCenter());
		success = true;
	}

	@Override
	public Worker branch()
	{
		return success ? new EnlistCharacter() : null;
	}

}
