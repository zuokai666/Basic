package com.zk.basic.task.config;

public interface QueryDbConfig extends ConnectionConfig{
	
	int getGridSize();
	
	int getThreadCount();
	
	String getExecuteSql();
	
	String getPrimaryKeyName();
}