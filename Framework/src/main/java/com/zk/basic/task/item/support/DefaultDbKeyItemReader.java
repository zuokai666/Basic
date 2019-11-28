package com.zk.basic.task.item.support;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zk.basic.task.config.QueryDbConfig;

public class DefaultDbKeyItemReader implements DbKeyItemReader{
	
	private static final Logger log = LoggerFactory.getLogger(DefaultDbKeyItemReader.class);
	
	private QueryDbConfig queryDbConfig;
	private String primaryKeyName;
	
	public DefaultDbKeyItemReader(QueryDbConfig queryDbConfig) {
		this.queryDbConfig = queryDbConfig;
		this.primaryKeyName = queryDbConfig.getPrimaryKeyName();
	}
	
	@Override
	public List<String> read() throws Exception {
		Connection connection = DriverManager.getConnection(
				queryDbConfig.getUrl(), 
				queryDbConfig.getUser(), 
				queryDbConfig.getPassword());
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(queryDbConfig.getExecuteSql());
		List<String> list = new ArrayList<String>();
		while(resultSet.next()){
			list.add(resultSet.getString(getPrimaryKeyName()));
		}
		log.debug("查询数据库,结果集大小:[{}]", list.size());
		return list;
	}
	
	@Override
	public String getPrimaryKeyName() {
		return primaryKeyName;
	}
}