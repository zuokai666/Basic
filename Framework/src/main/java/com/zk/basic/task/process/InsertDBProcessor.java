package com.zk.basic.task.process;

import java.util.concurrent.Executor;

import com.zk.basic.id.IdGenerator;

@Deprecated
public interface InsertDBProcessor {
	
	public void start(Executor executor, IdGenerator idGenerator);
	
	public void handle(String[] values);
	
	public void end();
}