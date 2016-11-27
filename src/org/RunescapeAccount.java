package org;
import java.util.concurrent.ThreadLocalRandom;


public class RunescapeAccount
{
	private static final int USERNAME_LENGTH = 9;
	private static final int MINIMUM_NUMBERS = 2;
	private static final String[] EMAIL_EXTENSIONS = {"@msn.com", "@hotmail.com", "@yahoo.com", "@gmail.com", "@live.com"};
	private static final NameGenerator NAME_GENERATOR = new NameGenerator(USERNAME_LENGTH, MINIMUM_NUMBERS);
	private static final NameGenerator PASS_GENERATOR = new NameGenerator(USERNAME_LENGTH, MINIMUM_NUMBERS);
	
	private String displayName;
	private String emailExtension;
	private String password;
	private int age;
	
	public RunescapeAccount()
	{
		NAME_GENERATOR.generate();
		PASS_GENERATOR.generate();
		this.age = random(18, 50);
		this.displayName = NAME_GENERATOR.getName();
		this.emailExtension = EMAIL_EXTENSIONS[random(0, EMAIL_EXTENSIONS.length - 1)];
		this.password = PASS_GENERATOR.getName();
	}
	
	private int random(int min, int max)
	{
		return ThreadLocalRandom.current().nextInt(min, max + 1);
	}
	
	//getters and setters
	public String getDisplayName()
	{
		return this.displayName;
	}
	
	public String getEmailExtension()
	{
		return this.emailExtension;
	}
	
	public String getEmail()
	{
		return displayName + emailExtension;
	}
	
	public String getPassword()
	{
		return this.password;
	}
	
	public int getAge()
	{
		return this.age;
	}
}
