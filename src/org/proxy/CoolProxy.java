package org.proxy;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Queue;

public class CoolProxy extends ProxyRotator
{
	private static final String URL = "http://api.coolproxies.com/api.php?list=1&l12=1&days=11&socks5=1&apikey=Y3I4FQRQU8GD9WZK";
	private static final String FILE_NAME = "cool_proxies";
	private static final Queue<Entry<String, String>> CACHE = new LinkedList<>();
	
	@Override
	public boolean grabInfo()
	{
		if(CACHE.isEmpty())
			downloadList();
		
		Entry<String, String> proxy = CACHE.poll();
		if(proxy == null) return false;
		
		ip = proxy.getKey();
		port = proxy.getValue();
		
		CACHE.add(proxy);
		return true;
	}
	
	private void downloadList()
	{	
		try
		{
			List<Entry<String, String>> list = new ArrayList<>();
			
			URL website = new URL(URL);
			ReadableByteChannel rbc = Channels.newChannel(website.openStream());
			FileOutputStream fos = new FileOutputStream(FILE_NAME);
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
			fos.close();
			
			FileReader fr = new FileReader(FILE_NAME);
			BufferedReader br = new BufferedReader(fr);
			String line;
			while((line = br.readLine()) != null)
			{
				String[] parts = line.split(":");
				list.add(new AbstractMap.SimpleEntry<>(parts[0], parts[1]));
			}
			
			br.close();
			Collections.shuffle(list);
			CACHE.addAll(list);
			System.out.println("Added " + CACHE.size() + " cool proxies");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
