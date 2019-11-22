package com.zk.basic.task.config;

import com.zk.basic.task.sql.SqlHolder;

public interface DBConfig {
	
	int getCommitCount();
	int getThreadCount();
	
	String getUrl();
    String getUser();
    String getPassword();
    
    SqlHolder getSqlHolder();
}