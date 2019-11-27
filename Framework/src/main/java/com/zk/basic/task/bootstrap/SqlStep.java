package com.zk.basic.task.bootstrap;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zk.basic.task.config.ConnectionConfig;
import com.zk.basic.task.sql.DefaultSqlHolder;
import com.zk.basic.task.sql.SqlHolder;

public class SqlStep extends AbstractStep{
	
	private static final Logger log = LoggerFactory.getLogger(SqlStep.class);
	
	private ConnectionConfig connectionConfig;
	private SqlHolder sqlHolder;
	
	public SqlStep(String name, ConnectionConfig connectionConfig, String sql) {
		super(name);
		this.connectionConfig = connectionConfig;
		SqlHolder sqlHolder = createSqlHolder();
		sqlHolder.addPreSql(sql);
		this.sqlHolder = sqlHolder;
	}
	
	protected SqlHolder createSqlHolder() {
		SqlHolder sqlHolder = new DefaultSqlHolder();
		return sqlHolder;
	}
	
	@Override
	protected void doExecute() throws Exception {
		Statement statement = null;
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(connectionConfig.getUrl(), connectionConfig.getUser(), connectionConfig.getPassword());
			statement = connection.createStatement();
			for(int i=0;i<sqlHolder.getPreSql().size();i++){
				String sql = sqlHolder.getPreSql().get(i);
				statement.execute(sql);
				log.debug("脚本执行：[{}]", sql);
			}
		} catch (SQLException e) {
			log.error("", e);
		} finally {
			if(statement != null){
				statement.close();
			}
			if(connection != null){
				connection.close();
			}
		}
	}
}