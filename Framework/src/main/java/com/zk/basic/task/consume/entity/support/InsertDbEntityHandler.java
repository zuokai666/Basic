package com.zk.basic.task.consume.entity.support;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zk.basic.beans.factory.Summer;
import com.zk.basic.id.IdGenerator;
import com.zk.basic.id.IdGenerators;
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
		IdGenerators idGenerators = Summer.rain().getBean(IdGenerators.class);
		this.idGenerator = idGenerators.getIdGenerator(insertDbConfig.getTable());
	}
	
	@Override
	public void start() throws Exception {
		connection = Summer.rain().getBean(DataSource.class).getConnection();
		connection.setAutoCommit(false);
		preparedStatement = connection.prepareStatement(insertDbConfig.getPreparedSql());
	}
	
	/**
	 * 2019年12月9日12:53:33 左凯
	 * 这里逻辑太死，直接增加主键生成，大大的问题。
	 */
	@Override
	public void handle(Object entity) throws Exception {
		String[] values = (String[])entity;
		preparedStatement.setObject(1, idGenerator.generateId());//FIXME 逻辑太死
		for(int i=0;i<values.length;i++){
			preparedStatement.setObject(i + 2, values[i]);
		}
		preparedStatement.addBatch();
		count++;
		if(count % insertDbConfig.getCommitCount() == 0){
			preparedStatement.executeBatch();
			connection.commit();
			preparedStatement.clearBatch();
			log.debug("插入表[{}],执行条数：[{}]", insertDbConfig.getTable(), count);
		}
	}
	
	@Override
	public void stop() throws Exception {
		if(preparedStatement != null) {
			preparedStatement.executeBatch();
			preparedStatement.clearBatch();
		}
		if(connection != null){
			connection.commit();
			connection.close();//回归连接池母亲的怀抱
		}
		log.debug("线程执行结束,插入表[{}],总执行条数：[{}]", insertDbConfig.getTable(), count);
	}
}