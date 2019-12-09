package com.zk.basic.task.bootstrap;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zk.basic.beans.factory.Summer;
import com.zk.basic.task.exception.StepExecuteException;

/**
 * 执行脚本的步骤
 * 
 * @author King
 *
 */
public class SqlStep extends AbstractStep{
	
	private static final Logger log = LoggerFactory.getLogger(SqlStep.class);
	
	private List<String> sqlHolder;
	
	public SqlStep(String name, String sql) {
		super(name);
		this.sqlHolder = new ArrayList<String>();
		this.sqlHolder.add(sql);
	}
	
	public void addPreSql(String sql){
		this.sqlHolder.add(sql);
	}
	
	@Override
	protected void doExecute() throws Exception {
		if(sqlHolder == null || sqlHolder.size() == 0){
			return;
		}
		Statement statement = null;
		Connection connection = null;
		try {
			connection = Summer.rain().getBean(DataSource.class).getConnection();
			connection.setAutoCommit(true);//此处应该是默认自动提交
			statement = connection.createStatement();
			for(int i=0;i<sqlHolder.size();i++){
				String sql = sqlHolder.get(i);
				statement.execute(sql);
				log.debug("脚本执行：[{}]", sql);
			}
		} catch (SQLException e) {
			log.error("", e);
			throw new StepExecuteException("步骤中断：" + e.getMessage());
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