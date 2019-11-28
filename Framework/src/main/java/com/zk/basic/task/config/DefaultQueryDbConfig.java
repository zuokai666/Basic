package com.zk.basic.task.config;

public class DefaultQueryDbConfig extends DefaultConnectionConfig implements QueryDbConfig{
	
	private int gridSize;
	private int threadCount;
	private String executeSql;
	private String primaryKeyName;
	
	public DefaultQueryDbConfig(int threadCount, 
			String url, String user, String password, String dataBase, 
			String executeSql, String primaryKeyName) {
		super(url, user, password, dataBase);
		this.gridSize = threadCount;
		this.threadCount = threadCount;
		this.executeSql = executeSql;
		this.primaryKeyName = primaryKeyName;
	}
	
	@Override
	public int getGridSize() {
		return gridSize;
	}
	@Override
	public int getThreadCount() {
		return threadCount;
	}
	@Override
	public String getExecuteSql() {
		return executeSql;
	}
	@Override
	public String getPrimaryKeyName() {
		return primaryKeyName;
	}
}