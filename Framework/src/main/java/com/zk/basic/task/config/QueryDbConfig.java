package com.zk.basic.task.config;

public interface QueryDbConfig {
	
	int getGridSize();
	
	int getThreadCount();
	
	String getExecuteSql();
	
	String getPrimaryKeyName();
}