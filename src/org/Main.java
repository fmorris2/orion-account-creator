package org;
public class Main
{

	public static void main(String[] args) throws InterruptedException
	{
		LocalAccountMerger merger = new LocalAccountMerger();
		
		while(true)
		{
			//run merger if needed
			if(merger.needsToMerge())
				merger.mergeAccounts();
			
			//create accounts
			AccountCreator c = new AccountCreator();
			if(c.create())
			{
				if(c.report())
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
