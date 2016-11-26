package org;

public class LocalAccountMerger
{
	private static final String ARCHIVE_FILE = "archive";
	private static final int TIME_INTERVAL = 60000 * 15; //Attempts every 15 mins
	
	private long lastMergeTime = System.currentTimeMillis();
	
	public void mergeAccounts()
	{
		
	}
	
	public boolean needsToMerge()
	{
		return System.currentTimeMillis() - lastMergeTime > TIME_INTERVAL;
	}
}
