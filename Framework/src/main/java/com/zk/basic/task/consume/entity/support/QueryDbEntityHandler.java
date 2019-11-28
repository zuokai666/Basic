package com.zk.basic.task.consume.entity.support;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zk.basic.task.config.FileWriterConfig;
import com.zk.basic.task.config.QueryDbConfig;
import com.zk.basic.task.consume.entity.EntityHandler;
import com.zk.basic.task.item.support.DefaultFileItemWriter;
import com.zk.basic.task.item.support.FileItemWriter;

public abstract class QueryDbEntityHandler implements EntityHandler{
	
	private static final Logger log = LoggerFactory.getLogger(QueryDbEntityHandler.class);
	
	private int count = 0;//私有变量
	protected QueryDbConfig queryDbConfig;
	protected Connection connection;
	protected Statement statement;
	protected FileWriterConfig fileWriterConfig;
	protected FileItemWriter fileItemWriter;
	
	public QueryDbEntityHandler(QueryDbConfig queryDbConfig, FileWriterConfig fileWriterConfig) {
		this.queryDbConfig = queryDbConfig;
		this.fileWriterConfig = fileWriterConfig;
	}
	
	@Override
	public void start() throws Exception {
		fileItemWriter = new DefaultFileItemWriter(fileWriterConfig);
		fileItemWriter.open();
		connection = DriverManager.getConnection(
				queryDbConfig.getUrl(), 
				queryDbConfig.getUser(), 
				queryDbConfig.getPassword());
		statement = connection.createStatement();
	}
	
	@Override
	public void handle(Object entity) throws Exception {
		String key = (String) entity;
		String result = doQuery(key);
		fileItemWriter.write(result);
		count++;
	}
	
	protected abstract String doQuery(String key) throws Exception;
	
	@Override
	public void stop() throws Exception {
		if(statement != null){
			statement.close();
		}
		if(connection != null){
			connection.close();
		}
		if(fileItemWriter != null){
			fileItemWriter.close();
		}
		String name = fileWriterConfig.getFilePath() + FileWriterConfig.FILE_END + 
				File.separator + Thread.currentThread().getName();
		log.debug("线程执行结束,写入文件[{}],总插入条数：[{}]", name, count);
	}
}