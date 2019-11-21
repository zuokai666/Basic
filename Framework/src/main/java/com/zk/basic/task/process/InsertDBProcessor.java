package com.zk.basic.task.process;

public interface InsertDBProcessor {
	
	public void start();
	
	public void handle(String[] values);
	
	public void end();
}