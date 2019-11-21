package com.zk.basic.task.config;

public class DefaultDBConfig implements DBConfig{

	private int commitCount;
	private int threadCount;
	
	public DefaultDBConfig(int commitCount, int threadCount) {
		this.commitCount = commitCount;
		this.threadCount = threadCount;
	}
	
	@Override
	public int getCommitCount() {
		return commitCount;
	}

	@Override
	public int getThreadCount() {
		return threadCount;
	}
}