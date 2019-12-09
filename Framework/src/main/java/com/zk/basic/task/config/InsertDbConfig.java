package com.zk.basic.task.config;

public interface InsertDbConfig {
	
	int getCommitCount();
	
	int getThreadCount();
	
	String getPreparedSql();
	
	String getTable();
}