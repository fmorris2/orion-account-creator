package org.db;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;

public class LocalAccountMerger
{
	public static final String ARCHIVE_FILE = "archive";
	private static final int TIME_INTERVAL = 60000 * 15; //Attempts every 15 mins
	
	private long lastMergeTime = System.currentTimeMillis();
	private DBManager db;
	
	public LocalAccountMerger(DBManager db)
	{
		this.db = db;
	}
	
	public void mergeAccounts()
	{
		try(FileReader fR = new FileReader(ARCHIVE_FILE);
			BufferedReader bR = new BufferedReader(fR))
		{
			String line;
			int numAccounts = 0;
			while((line = bR.readLine()) != null)
			{
				String[] parts = line.split(":");
				db.getStatement().setString(1, parts[0]);
				db.getStatement().setString(2, parts[1]);
				db.getStatement().addBatch();
				numAccounts++;
			}
			
			if(numAccounts > 0)
			{
				System.out.println("Merging batch of " + numAccounts + " with database");
				if(db.getStatement().executeBatch()[0] >= 0)
				{
					System.out.println("Clearing local archive file");
					PrintWriter pw = new PrintWriter(ARCHIVE_FILE);
					pw.close();
				}
				else
					System.out.println("Failed to execute merge");
			}
			
			lastMergeTime = System.currentTimeMillis();
				
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public boolean needsToMerge()
	{
		return System.currentTimeMillis() - lastMergeTime > TIME_INTERVAL;
	}
}
