package org.account;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


public class NameGenerator
{
	private final String DICTIONARY_PATH = "/org/dictionary.txt";
	
	private int length;
	private int minimumNumbers;
	private String name;
	private List<String> realWords = new ArrayList<>();
	
	public NameGenerator(int length, int minimumNumbers)
	{
		this.length = length;
		this.minimumNumbers = minimumNumbers;
		loadWords();
	}
	
	
	public void generate()
	{
		String baseWord = realWords.get(random(0, realWords.size() - 1));
		
		for(int i = baseWord.length(); i <= length; i++)
			baseWord += random(0, 9);
		
		name = baseWord;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void loadWords()
	{
		try
		{
			InputStreamReader fr = new InputStreamReader(this.getClass().getResourceAsStream(DICTIONARY_PATH));
			BufferedReader in = new BufferedReader(fr);

			String inputLine;
			while ((inputLine = in.readLine()) != null)
			{
				if(inputLine.length() <= (length - minimumNumbers))
				realWords.add(inputLine);
			}

			in.close();

			System.out.println("Added " + realWords.size() + " words to real words list.");

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private int random(int min, int max)
	{
		return ThreadLocalRandom.current().nextInt(min, max + 1);
	}
}
