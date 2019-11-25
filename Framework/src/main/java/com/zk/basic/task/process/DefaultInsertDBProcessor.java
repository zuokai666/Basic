package com.zk.basic.task.process;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

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
	private AtomicBoolean error = new AtomicBoolean(false);
	
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
		try {
			Connection connection = DriverManager.getConnection(dbConfig.getUrl(), dbConfig.getUser(), dbConfig.getPassword());
			connection.setAutoCommit(false);
			Statement statement = connection.createStatement();
			for(int i=0;i<dbConfig.getSqlHolder().getPreSql().size();i++){
				String sql = dbConfig.getSqlHolder().getPreSql().get(i);
				statement.execute(sql);
				log.debug("脚本执行：[{}]", sql);
			}
			statement.close();
			connection.close();
		} catch (SQLException e) {
			log.error("", e);
		}
	}
	
	@Override
	public void handle(String[] values) {
		if(error.get()){
			if(queue != null){
				for(int i=0;i<dbConfig.getThreadCount();i++){
					try {
						queue.put(EOD_FLAG);
					} catch (InterruptedException e) {
						log.error("", e);
					}
				}
			}
			throw new RuntimeException("批量线程异常");
		}
		try {
			queue.put(values);
		} catch (InterruptedException e) {
			log.error("", e);
		}
	}
	
	@Override
	public void end() {
		if(queue != null){
			for(int i=0;i<dbConfig.getThreadCount();i++){
				try {
					queue.put(EOD_FLAG);//期待着所有线程都能收到一个结束标志
				} catch (InterruptedException e) {
					log.error("", e);
				}
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
				int count = 0;
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
					count++;
					if(count % dbProcessor.dbConfig.getCommitCount() == 0){
						preparedStatement.executeBatch();
						connection.commit();
						preparedStatement.clearBatch();
						log.debug("执行条数：[{}]", count);
					}
				}
				preparedStatement.executeBatch();
				connection.commit();
				preparedStatement.clearBatch();
				log.debug("执行条数：[{}]", count);
			} catch (Throwable t) {
				dbProcessor.error.compareAndSet(false, true);
				log.error("", t);
			} finally {
				dbProcessor.countDownLatch.countDown();
			}
		}
	}
}