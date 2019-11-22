package com.zk.basic.task.sql;

import java.util.ArrayList;
import java.util.List;

public class DefaultSqlHolder implements SqlHolder{
	
	private String sql;
	private List<String> preSql = new ArrayList<String>();
	
	public DefaultSqlHolder(String sql) {
		this.sql = sql;
	}
	
	@Override
	public String getSql() {
		return sql;
	}

	@Override
	public List<String> getPreSql() {
		return preSql;
	}

	@Override
	public void addPreSql(String sql) {
		preSql.add(sql);
	}
	
	
}