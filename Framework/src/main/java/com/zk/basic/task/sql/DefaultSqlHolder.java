package com.zk.basic.task.sql;

public class DefaultSqlHolder implements SqlHolder{
	
	private String sql;
	
	public DefaultSqlHolder(String sql) {
		this.sql = sql;
	}
	
	@Override
	public String getSql() {
		return sql;
	}
}