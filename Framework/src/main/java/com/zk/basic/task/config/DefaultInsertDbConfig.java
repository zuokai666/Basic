package com.zk.basic.task.config;

public class DefaultInsertDbConfig implements InsertDbConfig{
	
	private int commitCount;
	private int threadCount;
	private String sql;
	private String table;
	
	public DefaultInsertDbConfig(int threadCount, int commitCount, String sql, String table) {
		this.threadCount = threadCount;
		this.commitCount = commitCount;
		this.sql = sql;
		this.table = table;
	}
	
	@Override
	public int getCommitCount() {
		return commitCount;
	}
	
	@Override
	public String getPreparedSql() {
		return sql;
	}
	
	@Override
	public int getThreadCount() {
		return threadCount;
	}

	@Override
	public String getTable() {
		return table;
	}
}