package org.worker;

public interface Worker
{
	public void execute();
	public Worker branch();
}
