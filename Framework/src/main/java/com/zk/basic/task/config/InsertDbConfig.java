package com.zk.basic.task.config;

public interface InsertDbConfig extends ConnectionConfig, IdGenerateConfig{
	
	int getCommitCount();
	
	String getPreparedSql();
}