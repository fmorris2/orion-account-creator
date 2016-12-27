package org;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.sikuli.api.ImageTarget;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.Target;

public class Utils
{
	private static final String WEBSITE = "https://vikingscripts.io/oac/images/";
	private static final TrustManager[] TRUST_MANAGER = getTrustManager();
	private static final Map<String, BufferedImage> IMAGE_CACHE = new HashMap<>();
	private static final Map<BufferedImage, ScreenRegion> SUBREGION_CACHE = new HashMap<>();
	
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
	
	public static BufferedImage websiteUrl(String fileName)
	{
		try
		{
			BufferedImage cached = IMAGE_CACHE.get(fileName);
			
			if(cached == null)
			{
				installTrustManager();
				System.out.println("Accessing image at URL: " + WEBSITE + fileName);
				cached = ImageIO.read(new URL(WEBSITE + fileName).openStream());
			}
			
			return cached;
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static ScreenRegion waitFor(BufferedImage image, int timeout)
	{
		return waitFor(image, timeout, 0.7);
	}
	
	public static ScreenRegion waitFor(BufferedImage image, int timeout, double threshold)
	{
		long start = System.currentTimeMillis();
		ScreenRegion cached = SUBREGION_CACHE.get(image);
		Target targ = new ImageTarget(image);
		targ.setMinScore(threshold);
		
		if(cached == null) //subregion not in cache - must search entire screen
		{
			System.out.println("Subregion not found in cache, searching entire screen for image");	
			cached = AccountCreator.screen.wait(targ, timeout);
			SUBREGION_CACHE.put(image, cached);
		}
		else //subregion in cache, search in it
		{
			System.out.println("Subregion found in cache, searching in it for image");
			ScreenRegion found = cached.wait(targ, timeout);
			if(found == null)
			{
				System.out.println("Image not found in cached subregion! Resetting cache entry...");
				SUBREGION_CACHE.remove(image);
				return waitFor(image, timeout);
			}
			
			System.out.println("Image found in cached subregion - found in " + (System.currentTimeMillis() - start) + "ms");
			return found;
		}
		
		if(cached != null)
			System.out.println("Image found in entire screen search - found in " + (System.currentTimeMillis() - start) + "ms");
		else
			System.out.println("Image could not be found in entire screen search");
		
		return cached;
	}
	
	private static void installTrustManager()
	{
		try 
		{
		    SSLContext sc = SSLContext.getInstance("SSL"); 
		    sc.init(null, TRUST_MANAGER, new java.security.SecureRandom()); 
		    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		} 
		catch (GeneralSecurityException e) 
		{
			e.printStackTrace();
		} 
	}
	
	private static TrustManager[] getTrustManager()
	{
		// Create a trust manager that does not validate certificate chains
		return new TrustManager[] 
		{ 
			new X509TrustManager()
			{
				public java.security.cert.X509Certificate[] getAcceptedIssuers()
				{
					return new X509Certificate[0];
				}
	
				public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType)
				{}
	
				public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType)
				{}
			} 
		};
	}
}
