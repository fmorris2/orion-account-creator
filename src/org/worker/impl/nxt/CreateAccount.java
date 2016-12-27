package org.worker.impl.nxt;

import java.awt.image.BufferedImage;

import org.AccountCreator;
import org.Utils;
import org.sikuli.api.ScreenRegion;
import org.worker.Worker;

public class CreateAccount implements Worker
{
	private static final int LOADING_WAIT_TIME = 30000;
	private static final int DONE_BUTTON_WAIT_TIME = 15000;

	private static BufferedImage createAccButton = Utils.websiteUrl("createAccount.png");
	private static BufferedImage doneButton = Utils.websiteUrl("doneButton.png");
	
	private boolean success;
	
	@Override
	public void execute()
	{
		ScreenRegion accButton = Utils.waitFor(createAccButton, LOADING_WAIT_TIME, 0.9);
		if(accButton == null)
			return;
		
		System.out.println("Create account button found... clicking on it");
		
		AccountCreator.mouse.click(accButton.getCenter());
		
		ScreenRegion done = Utils.waitFor(doneButton, DONE_BUTTON_WAIT_TIME, 0.9);
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
