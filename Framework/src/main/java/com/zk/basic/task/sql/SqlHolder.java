package com.zk.basic.task.sql;

import java.util.List;

public interface SqlHolder {
	
	List<String> getPreSql();
	
	void addPreSql(String sql);
	
	String getSql();
}