package com.zk.basic.task.sql;

import java.util.ArrayList;
import java.util.List;

public class DefaultSqlHolder implements SqlHolder{
	
	private List<String> preSql = new ArrayList<String>();
	
	@Override
	public List<String> getPreSql() {
		return preSql;
	}

	@Override
	public void addPreSql(String sql) {
		preSql.add(sql);
	}	
}