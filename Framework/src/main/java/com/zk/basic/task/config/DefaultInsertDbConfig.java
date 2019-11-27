package com.zk.basic.task.config;

import com.zk.basic.id.impl.DefaultIdGeneratorProperties;

public class DefaultInsertDbConfig implements InsertDbConfig{
	
	private int commitCount;
	
	private String url;
	private String user;
    private String password;
    
    private String dataCenterId = DefaultIdGeneratorProperties.dataCenterId;
	private String machineId = DefaultIdGeneratorProperties.machineId;
	private String processId = DefaultIdGeneratorProperties.processId;
	
	private String sql;
	
	public DefaultInsertDbConfig(int commitCount, String url, String user, String password, String sql) {
		this.commitCount = commitCount;
		this.url = url;
		this.user = user;
		this.password = password;
		this.sql = sql;
	}
	
	@Override
	public int getCommitCount() {
		return commitCount;
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
}