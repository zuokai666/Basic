package com.zk.basic.dbcp.test;

import java.sql.Connection;
import java.util.Random;
import java.util.concurrent.locks.LockSupport;

import org.slf4j.LoggerFactory;

import com.zk.basic.lamborghini.LamborghiniDataSource;
import com.zk.basic.lamborghini.SimpleLamborghiniConfig;

public class LamborghiniCPTest {
	
	private static final org.slf4j.Logger log = LoggerFactory.getLogger(LamborghiniCPTest.class);
	
	public static void main(String[] args) {
		SimpleLamborghiniConfig lamborghiniConfig = new SimpleLamborghiniConfig();
		lamborghiniConfig.setConnectionTimeout(20);
		lamborghiniConfig.setMaxPoolSize(1);
		lamborghiniConfig.setMinIdle(1);
		lamborghiniConfig.setUrl("jdbc:mysql://localhost:3306/test");
		lamborghiniConfig.setDriverClassName("com.mysql.jdbc.Driver");
		lamborghiniConfig.setUser("root");
		lamborghiniConfig.setPassword("d2p9bupt");
		
		LamborghiniDataSource dataSource = new LamborghiniDataSource(lamborghiniConfig);
		new Thread(() -> run1(dataSource)).start();
		new Thread(() -> run1(dataSource)).start();
		LockSupport.park();
	}
	
	public static void whileRun(LamborghiniDataSource dataSource){
		while(true){
			run(dataSource);
		}
	}
	
	public static void run(LamborghiniDataSource dataSource){
		try {
			Connection connection = dataSource.getConnection();
			log.debug("开始使用{}", connection.toString());
			Thread.sleep(new Random().nextInt(5) * 1000);
			log.debug("准备归还{}", connection.toString());
			connection.close();
			Thread.sleep(new Random().nextInt(5) * 1000);
		} catch (Exception e) {
			log.error("", e);
		}
	}
	
	public static void run1(LamborghiniDataSource dataSource){
		try {
			log.debug("开始获取");
			Connection connection = dataSource.getConnection();
			log.debug("开始使用{}", connection.toString());
			LockSupport.park();
		} catch (Exception e) {
			log.error("", e);
		}
	}
}