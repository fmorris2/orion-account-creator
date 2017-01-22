package org.db;

import java.io.BufferedWriter;
import java.io.FileWriter;


public class AccountRecorder
{	
	private FileWriter fileWriter;
	private BufferedWriter bufferedWriter;
	
	public AccountRecorder()
	{
		try
		{
			fileWriter = new FileWriter(LocalAccountMerger.ARCHIVE_FILE, true);
			bufferedWriter = new BufferedWriter(fileWriter);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void recordAccount(String email, String password)
	{
		try
		{
			bufferedWriter.write(email + ":" + password);
			bufferedWriter.newLine();
			bufferedWriter.flush();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
