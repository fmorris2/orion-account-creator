package org;

import org.db.DBManager;
import org.db.LocalAccountMerger;

public class Main
{
	public static void main(String[] args) throws InterruptedException
	{
		DBManager db = new DBManager();
		System.out.println(db.initialize() ? "Successfully connected to Orion database" : "Could not connect to Orion database");
		
		LocalAccountMerger merger = new LocalAccountMerger(db);
		
		while(true)
		{
			//run merger if needed
			if(merger.needsToMerge())
				merger.mergeAccounts();
			
			//create accounts
			AccountCreator c = new AccountCreator();
			if(c.create())
			{
				if(db.report(AccountCreator.email, AccountCreator.password))
					System.out.println("Successfully stored account in database");
				else
				{
					System.out.println("Storing account locally");
					c.storeLocally();
				}
			}
			else
				System.out.println("Failed to create account");
			
			c.killProcesses();
			
			Thread.sleep(1000);
		}
	}
	
}
