package org.worker.impl.nxt;

import org.AccountCreator;
import org.Utils;
import org.sikuli.api.ImageTarget;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.Target;
import org.worker.Worker;

public class CreateAccount implements Worker
{
	private static final int LOADING_WAIT_TIME = 30000;
	private static final int DONE_BUTTON_WAIT_TIME = 15000;
	
	private Target createAccButton = new ImageTarget(Utils.fileUrl("images/createAccount.png"));
	private Target doneButton = new ImageTarget(Utils.fileUrl("images/doneButton.png"));
	
	private boolean success;
	
	@Override
	public void execute()
	{
		createAccButton.setMinScore(0.9);
		ScreenRegion accButton = AccountCreator.screen.wait(createAccButton, LOADING_WAIT_TIME);
		if(accButton == null)
			return;
		
		System.out.println("Create account button found... clicking on it");
		
		AccountCreator.mouse.click(accButton.getCenter());
		
		doneButton.setMinScore(0.9);
		ScreenRegion done = AccountCreator.screen.wait(doneButton, DONE_BUTTON_WAIT_TIME);
		if(done == null)
			return;
		
		System.out.println("Done button found... clicking on it");
		
		AccountCreator.mouse.click(done.getCenter());
		success = true;
	}

	@Override
	public Worker branch()
	{
		return success ? new EnlistCharacter() : null;
	}

}
