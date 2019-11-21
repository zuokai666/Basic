package com.zk.basic.task.process;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import com.zk.basic.task.config.DBConfig;

public class DefaultInsertDBProcessor implements InsertDBProcessor{

	private DBConfig dbConfig;
	private BlockingQueue<String[]> queue;
	
	public DefaultInsertDBProcessor(DBConfig dbConfig) {
		this.dbConfig = dbConfig;
		queue = new ArrayBlockingQueue<>(dbConfig.getThreadCount() * 10);
	}
	
	@Override
	public void start() {
	}

	@Override
	public void handle(String[] values) {
	}

	@Override
	public void end() {
	}
}