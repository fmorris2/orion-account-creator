package org.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class DBManager
{
	private static final String DB_NAME = "orion";
	private static final String IP = "45.55.243.185";
	private static final String PORT = "3306";
	private static final String USERNAME = "remote";
	private static final String PASSWORD = "diesonne30";
	
	private static final int CONNECTION_TIMEOUT = 10000;
	private static final String INSERT_NEW_ACC = "INSERT INTO account (email, password) VALUES (?, ?)";
	
	private Connection connection;
	private PreparedStatement statement;
	private Properties connectionProps = new Properties();
	
	public boolean initialize()
	{
	    connectionProps.put("user", USERNAME);
	    connectionProps.put("password", PASSWORD);
	    
	    try
	    {
	    	connection = DriverManager.getConnection("jdbc:mysql://"+IP+":"+PORT+"/"+DB_NAME,connectionProps);
	    	boolean success = connection.isValid(CONNECTION_TIMEOUT);
	    	if(success)
	    		statement = connection.prepareStatement(INSERT_NEW_ACC);
	    	return success;
	    }
	    catch(SQLException e)
	    {
	    	e.printStackTrace();
	    }
	  
	    return false;
	}
	
	public boolean report(String email, String pass)
	{
		try
		{
			if(!connection.isValid(CONNECTION_TIMEOUT))
			{
				connection = DriverManager.getConnection("jdbc:mysql://"+IP+":"+PORT+"/"+DB_NAME,connectionProps);
				statement = connection.prepareStatement(INSERT_NEW_ACC);
			}
			
			statement.setString(1,  email);
			statement.setString(2, pass);
			statement.execute();
			return true;
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		return false;
	}
	
	public PreparedStatement getStatement()
	{
		return statement;
	}
}
