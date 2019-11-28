package com.zk.basic.task.consume.entity.support;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zk.basic.id.IdGenerator;
import com.zk.basic.id.IdGeneratorManager;
import com.zk.basic.task.config.InsertDbConfig;
import com.zk.basic.task.consume.entity.EntityHandler;

public class InsertDbEntityHandler implements EntityHandler{
	
	private static final Logger log = LoggerFactory.getLogger(InsertDbEntityHandler.class);
	
	private InsertDbConfig insertDbConfig;
	private int count = 0;//私有变量
	private Connection connection;
	private PreparedStatement preparedStatement;
	private IdGenerator idGenerator;
	
	public InsertDbEntityHandler(InsertDbConfig insertDbConfig) {
		this.insertDbConfig = insertDbConfig;
		this.idGenerator = IdGeneratorManager.getByTable(insertDbConfig);
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
		preparedStatement.setObject(1, idGenerator.generateId());
		for(int i=0;i<values.length;i++){
			preparedStatement.setObject(i + 2, values[i]);
		}
		preparedStatement.addBatch();
		count++;
		if(count % insertDbConfig.getCommitCount() == 0){
			preparedStatement.executeBatch();
			connection.commit();
			preparedStatement.clearBatch();
			log.debug("插入表[{}.{}],执行条数：[{}]", insertDbConfig.getDataBase(), insertDbConfig.getTableName(), count);
		}
	}
	
	@Override
	public void stop() throws Exception {
		preparedStatement.executeBatch();
		connection.commit();
		preparedStatement.clearBatch();
		log.debug("线程执行结束,插入表[{}.{}],总执行条数：[{}]", insertDbConfig.getDataBase(), insertDbConfig.getTableName(), count);
	}
}