package org;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class Utils
{
	public static URL fileUrl(String filePath)
	{
		try
		{
			return new File(filePath).toURI().toURL();
		}
		catch(MalformedURLException e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
}
