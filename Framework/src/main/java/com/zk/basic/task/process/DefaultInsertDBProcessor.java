package com.zk.basic.task.process;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zk.basic.id.IdGenerator;
import com.zk.basic.task.config.DBConfig;
import com.zk.basic.task.sql.SqlHolder;

public class DefaultInsertDBProcessor implements InsertDBProcessor{
	
	private static final Logger log = LoggerFactory.getLogger(DefaultInsertDBProcessor.class);
	
	private DBConfig dbConfig;
	private BlockingQueue<String[]> queue;
	private Executor executor;
	private CountDownLatch countDownLatch;
	private String[] EOD_FLAG = new String[1];
	private IdGenerator idGenerator;
	
	public DefaultInsertDBProcessor(DBConfig dbConfig) {
		this.dbConfig = dbConfig;
	}
	
	@Override
	public void start(Executor executor, IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
		this.executor = executor;
		this.queue = new ArrayBlockingQueue<>(dbConfig.getThreadCount() * 10);
		this.countDownLatch = new CountDownLatch(dbConfig.getThreadCount());
		for(int i=0;i<dbConfig.getThreadCount();i++){
			this.executor.execute(new InsertDbRunnable(DefaultInsertDBProcessor.this));
		}
	}
	
	@Override
	public void handle(String[] values) {
		queue.add(values);
	}
	
	@Override
	public void end() {
		if(queue != null){
			for(int i=0;i<dbConfig.getThreadCount();i++){
				queue.add(EOD_FLAG);
			}
			try {
				countDownLatch.await();
			} catch (InterruptedException e) {
				log.error("", e);
			}
			log.debug("文件落库完成");
		}
	}
	
	public class InsertDbRunnable implements Runnable{
		
		private final Logger log = LoggerFactory.getLogger(InsertDbRunnable.class);
		
		private DefaultInsertDBProcessor dbProcessor;
		
		public InsertDbRunnable(DefaultInsertDBProcessor dbProcessor) {
			this.dbProcessor = dbProcessor;
		}
		
		@Override
		public void run() {
			try {
				SqlHolder sqlHolder = dbProcessor.dbConfig.getSqlHolder();
				Connection connection = DriverManager.getConnection(
								dbProcessor.dbConfig.getUrl(), 
								dbProcessor.dbConfig.getUser(), 
								dbProcessor.dbConfig.getPassword());
				connection.setAutoCommit(false);
				PreparedStatement preparedStatement = connection.prepareStatement(sqlHolder.getSql());
				String[] values = null;
				while((values = dbProcessor.queue.take()) != EOD_FLAG){
					preparedStatement.setObject(1, idGenerator.generateId());
					for(int i=0;i<values.length;i++){
						preparedStatement.setObject(i + 2, values[i]);
					}
					preparedStatement.addBatch();
				}
				preparedStatement.executeBatch();
				connection.commit();
			} catch (Throwable t) {
				log.error("", t);
			} finally {
				dbProcessor.countDownLatch.countDown();
			}
		}
	}
}