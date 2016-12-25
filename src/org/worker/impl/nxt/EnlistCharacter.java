package org.worker.impl.nxt;

import org.AccountCreator;
import org.RunescapeAccount;
import org.Utils;
import org.sikuli.api.ImageTarget;
import org.sikuli.api.Relative;
import org.sikuli.api.ScreenLocation;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.Target;
import org.worker.Worker;

public class EnlistCharacter implements Worker
{
	private static final int LOBBY_WAIT_TIME = 15000;
	private static final int FAIL_WAIT_TIME = 5000;
	
	private Target randomNameButton = new ImageTarget(Utils.fileUrl("images/randomName.png"));
	private Target ageLabel = new ImageTarget(Utils.fileUrl("images/age.png"));
	private Target emailLabel = new ImageTarget(Utils.fileUrl("images/email.png"));
	private Target passwordLabel = new ImageTarget(Utils.fileUrl("images/password.png"));
	private Target playNowButton = new ImageTarget(Utils.fileUrl("images/playNow.png"));
	private Target failureLabel = new ImageTarget(Utils.fileUrl("images/cooldown.png"));
	private Target lobbyLabel = new ImageTarget(Utils.fileUrl("images/lobby.png"));
	
	private boolean success;
	private RunescapeAccount account = new RunescapeAccount();
	
	@Override
	public void execute()
	{
		//Randomize displayName
		randomNameButton.setMinScore(0.95);
		ScreenRegion randomName = AccountCreator.screen.wait(randomNameButton, AccountCreator.GENERAL_WAIT_TIME);
		if(randomName == null)
			return;
		AccountCreator.mouse.click(randomName.getCenter());
		
		//fill fields
		if(!fill(ageLabel, ""+account.getAge()) || !fill(emailLabel, account.getEmail()) 
				|| !fill(passwordLabel, account.getPassword()))
			return;
		
		//click done
		ScreenRegion playNow = AccountCreator.screen.wait(playNowButton, AccountCreator.GENERAL_WAIT_TIME);
		if(playNow == null)
			return;
		AccountCreator.mouse.click(playNow.getCenter());
		
		//verify account has been created
		ScreenRegion lobby = AccountCreator.screen.wait(lobbyLabel, LOBBY_WAIT_TIME);
		ScreenRegion failure = AccountCreator.screen.wait(failureLabel, FAIL_WAIT_TIME);
		if(failure != null)
			AccountCreator.onCooldown = true;
		else if(lobby != null)
		{
			success = true;
			AccountCreator.email = account.getEmail();
			AccountCreator.password = account.getPassword();
		}
	}

	@Override
	public Worker branch()
	{
		return success ? this : null;
	}
	
	private boolean fill(Target t, String s)
	{
		ScreenRegion r = AccountCreator.screen.wait(t, AccountCreator.GENERAL_WAIT_TIME);
		if(r == null)
			return false;
		
		ScreenLocation toClick = Relative.to(r.getCenter()).right(90).getScreenLocation();
		AccountCreator.mouse.doubleClick(toClick);
		
		try	{ Thread.sleep(450); }
		catch(InterruptedException e) { e.printStackTrace(); }
		
		AccountCreator.keyboard.type(s);
		return true;
	}
	
}
