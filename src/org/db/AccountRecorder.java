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
	
	public void recordAccount(String email, String password, String ip, String port, String user, String pass)
	{
		try
		{
			bufferedWriter.write(email + ":" + password + ":" + ip + ":" + port + ":" + user + ":" + pass);
			bufferedWriter.newLine();
			bufferedWriter.flush();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
