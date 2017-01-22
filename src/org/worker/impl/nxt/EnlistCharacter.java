package org.worker.impl.nxt;

import java.awt.image.BufferedImage;

import org.AccountCreator;
import org.Utils;
import org.account.RunescapeAccount;
import org.sikuli.api.Relative;
import org.sikuli.api.ScreenLocation;
import org.sikuli.api.ScreenRegion;
import org.worker.Worker;

public class EnlistCharacter implements Worker
{
	private static final int LOBBY_WAIT_TIME = 15000;
	private static final int FAIL_WAIT_TIME = 2000;
	
	private static BufferedImage randomNameButton = Utils.websiteUrl("randomName.png");
	private static BufferedImage ageLabel = Utils.websiteUrl("age.png");
	private static BufferedImage emailLabel = Utils.websiteUrl("email.png");
	private static BufferedImage passwordLabel = Utils.websiteUrl("password.png");
	private static BufferedImage playNowButton = Utils.websiteUrl("playNow.png");
	private static BufferedImage failureLabel = Utils.websiteUrl("cooldown.png");
	private static BufferedImage lobbyLabel = Utils.websiteUrl("lobby.png");
	private static BufferedImage emailVerification = Utils.websiteUrl("emailVerification.png");
	
	private boolean success;
	private RunescapeAccount account = new RunescapeAccount();
	
	@Override
	public void execute()
	{
		//Randomize displayName
		ScreenRegion randomName = Utils.waitFor(randomNameButton, AccountCreator.GENERAL_WAIT_TIME, 0.95);
		if(randomName == null)
			return;
		AccountCreator.mouse.click(randomName.getCenter());
		
		//fill fields
		if(!fill(ageLabel, ""+account.getAge()) 
				|| !fill(emailLabel, account.getEmail()) || (Utils.waitFor(emailVerification, 3000) == null)
				|| !fill(passwordLabel, account.getPassword()))
			return;
		
		//click done
		ScreenRegion playNow = Utils.waitFor(playNowButton, AccountCreator.GENERAL_WAIT_TIME);
		if(playNow == null)
			return;
		AccountCreator.mouse.click(playNow.getCenter());
		
		//verify account has been created
		ScreenRegion lobby = Utils.waitFor(lobbyLabel, LOBBY_WAIT_TIME);
		ScreenRegion failure = Utils.waitFor(failureLabel, FAIL_WAIT_TIME);
		if(failure != null)
		{
			System.out.println("Putting " + AccountCreator.proxies.getCurrentSubnet() + " on cooldown");
			AccountCreator.proxies.getCurrentSubnet().cooldown();
		}
		else if(lobby != null)
		{
			success = true;
			AccountCreator.email = account.getEmail();
			AccountCreator.password = account.getPassword();
		}
	}
	
	private void waitMs(long ms)
	{
		try	{ Thread.sleep(ms); }
		catch(InterruptedException e) { e.printStackTrace(); }
	}

	@Override
	public Worker branch()
	{
		return success ? this : null;
	}
	
	private boolean fill(BufferedImage t, String s)
	{
		ScreenRegion r = Utils.waitFor(t, AccountCreator.GENERAL_WAIT_TIME);
		if(r == null)
			return false;
		
		ScreenLocation toClick = Relative.to(r.getCenter()).right(90).getScreenLocation();
		AccountCreator.mouse.doubleClick(toClick);
		
		waitMs(150);
		
		AccountCreator.keyboard.type(s);
		
		return true;
	}
	
}
