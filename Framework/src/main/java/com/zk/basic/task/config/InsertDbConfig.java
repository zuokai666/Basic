package com.zk.basic.task.config;

import com.zk.basic.id.IdGenerateConfig;

public interface InsertDbConfig extends ConnectionConfig, IdGenerateConfig{
	
	int getCommitCount();
	
	int getThreadCount();
	
	String getPreparedSql();
}