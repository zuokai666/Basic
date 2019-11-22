package com.zk.basic.task.config;

import com.zk.basic.task.sql.SqlHolder;

public class DefaultDBConfig implements DBConfig{
	
	private int commitCount;
	private int threadCount;
	private String url;
	private String user;
    private String password;
    private SqlHolder sqlHolder;
	
	public DefaultDBConfig(int threadCount, int commitCount, String url, String user, String password, SqlHolder sqlHolder) {
		this.commitCount = commitCount;
		this.threadCount = threadCount;
		this.url = url;
		this.user = user;
		this.password = password;
		this.sqlHolder = sqlHolder;
	}
	
	@Override
	public int getCommitCount() {
		return commitCount;
	}

	@Override
	public int getThreadCount() {
		return threadCount;
	}

	@Override
	public String getUrl() {
		return url;
	}

	@Override
	public String getUser() {
		return user;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public SqlHolder getSqlHolder() {
		return sqlHolder;
	}
}