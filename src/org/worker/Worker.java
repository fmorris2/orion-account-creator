package org.worker;

public interface Worker
{
	public void execute();
	public boolean canExecute();
	public Worker branch();
}
