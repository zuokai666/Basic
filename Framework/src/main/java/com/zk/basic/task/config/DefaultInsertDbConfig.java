package com.zk.basic.task.config;

import com.zk.basic.id.impl.DefaultIdGeneratorProperties;

public class DefaultInsertDbConfig extends DefaultConnectionConfig implements InsertDbConfig{
	
	private int commitCount;
	private int threadCount;
    
    private String dataCenterId = DefaultIdGeneratorProperties.dataCenterId;
	private String machineId = DefaultIdGeneratorProperties.machineId;
	private String processId = DefaultIdGeneratorProperties.processId;
	
	private String sql;
	private String tableName;
	
	public DefaultInsertDbConfig(int threadCount, int commitCount, 
			String url, String user, String password, String sql, String dataBase, String tableName) {
		super(url, user, password, dataBase);
		this.threadCount = threadCount;
		this.commitCount = commitCount;
		this.sql = sql;
		this.tableName = tableName;
	}
	
	@Override
	public int getCommitCount() {
		return commitCount;
	}

	@Override
	public String getDataCenterId() {
		return dataCenterId;
	}

	@Override
	public String getMachineId() {
		return machineId;
	}

	@Override
	public String getProcessId() {
		return processId;
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
	public String getTableName() {
		return tableName;
	}
}