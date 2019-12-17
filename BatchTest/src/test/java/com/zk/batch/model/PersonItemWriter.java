package com.zk.batch.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;

import com.zk.basic.beans.factory.Summer;

public class PersonItemWriter implements ItemWriter<PersonEntity>{
	
	private static final Logger log = LoggerFactory.getLogger(PersonItemWriter.class);
	
	@Override
	public void write(List<? extends PersonEntity> items) throws Exception {
		log.debug("receive [{}] items.", items.size());
		Connection connection = null;
		try {
			connection = Summer.rain().getBean(DataSource.class).getConnection();
			connection.setAutoCommit(false);
			PreparedStatement preparedStatement = connection.prepareStatement("insert into Person(no,name,sex,age) values(?,?,?,?)");
			for(int i=0;i<items.size();i++){
				PersonEntity entity = items.get(i);
				preparedStatement.setObject(1, entity.getSerial());
				preparedStatement.setObject(2, entity.getName());
				preparedStatement.setObject(3, entity.getSex());
				preparedStatement.setObject(4, entity.getAge());
				preparedStatement.addBatch();
			}
			preparedStatement.executeBatch();
			connection.commit();
			preparedStatement.clearBatch();
			preparedStatement.close();
			log.debug("插入表[Person]成功,执行条数：[{}]", items.size());
		} catch (Exception e) {
			log.error("", e);
			if(connection != null){
				connection.rollback();
			}
		} finally {
			if(connection != null){
				connection.close();
			}
		}
	}
}