package com.zk.basic.task.consume.entity.support;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zk.basic.id.IdGenerator;
import com.zk.basic.id.impl.SimpleIdGenerator;
import com.zk.basic.task.config.InsertDbConfig;
import com.zk.basic.task.consume.entity.EntityHandler;

public class InsertDbEntityHandler implements EntityHandler{
	
	private static final Logger log = LoggerFactory.getLogger(InsertDbEntityHandler.class);
	
	private InsertDbConfig insertDbConfig;
	private int count = 0;
	private Connection connection;
	private PreparedStatement preparedStatement;
	private static volatile IdGenerator idGenerator;
	
	public InsertDbEntityHandler(InsertDbConfig insertDbConfig) {
		this.insertDbConfig = insertDbConfig;
	}
	
	public IdGenerator getIdGenerator(){
		IdGenerator cs = idGenerator;
		if (cs == null) {
			synchronized (IdGenerator.class) {
				cs = idGenerator;
				if (cs == null) {
					cs = new SimpleIdGenerator(insertDbConfig);
					idGenerator = cs;
				}
			}
		}
		return cs;
	}
	
	@Override
	public void start() throws Exception {
		connection = DriverManager.getConnection(
				insertDbConfig.getUrl(), 
				insertDbConfig.getUser(), 
				insertDbConfig.getPassword());
		connection.setAutoCommit(false);
		preparedStatement = connection.prepareStatement(insertDbConfig.getPreparedSql());
	}
	
	@Override
	public void handle(Object entity) throws Exception {
		String[] values = (String[])entity;
		preparedStatement.setObject(1, getIdGenerator().generateId());
		for(int i=0;i<values.length;i++){
			preparedStatement.setObject(i + 2, values[i]);
		}
		preparedStatement.addBatch();
		count++;
		if(count % insertDbConfig.getCommitCount() == 0){
			preparedStatement.executeBatch();
			connection.commit();
			preparedStatement.clearBatch();
			log.debug("执行条数：[{}]", count);
		}
	}
	
	@Override
	public void stop() throws Exception {
		preparedStatement.executeBatch();
		connection.commit();
		preparedStatement.clearBatch();
		log.debug("执行条数：[{}]", count);
	}
}