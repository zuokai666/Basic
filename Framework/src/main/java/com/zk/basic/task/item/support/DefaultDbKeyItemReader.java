package com.zk.basic.task.item.support;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zk.basic.beans.factory.Summer;
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
		List<String> list = new ArrayList<String>();
		Connection connection = null;
		try {
			connection = Summer.rain().getBean(DataSource.class).getConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(queryDbConfig.getExecuteSql());
			while(resultSet.next()){
				list.add(resultSet.getString(getPrimaryKeyName()));
			}
		} catch (Exception e) {
			log.error("", e);
		} finally {
			if(connection != null){//2019年12月9日13:09:29 左凯 发现没有连接关闭操作，是很愚蠢的行为
				connection.close();
			}
		}
		
		log.debug("查询数据库,结果集大小:[{}]", list.size());
		return list;
	}
	
	@Override
	public String getPrimaryKeyName() {
		return primaryKeyName;
	}
}