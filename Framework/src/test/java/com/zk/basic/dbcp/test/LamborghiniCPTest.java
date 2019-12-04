package com.zk.basic.dbcp.test;

import java.sql.Connection;
import java.util.concurrent.locks.LockSupport;

import org.slf4j.LoggerFactory;

import com.zk.basic.lamborghini.LamborghiniDataSource;
import com.zk.basic.lamborghini.SimpleLamborghiniConfig;

public class LamborghiniCPTest {
	
	private static final org.slf4j.Logger log = LoggerFactory.getLogger(LamborghiniCPTest.class);
	
	public static void main(String[] args) {
		SimpleLamborghiniConfig lamborghiniConfig = new SimpleLamborghiniConfig();
		lamborghiniConfig.setConnectionTimeout(30 * 1000);
		lamborghiniConfig.setMaxPoolSize(10);
		lamborghiniConfig.setMinIdle(3);
		lamborghiniConfig.setUrl("jdbc:mysql://localhost:3306/test");
		lamborghiniConfig.setDriverClassName("com.mysql.jdbc.Driver");
		lamborghiniConfig.setUser("root");
		lamborghiniConfig.setPassword("d2p9bupt");
		lamborghiniConfig.setPoolName("LamborghiniPool");
		
		LamborghiniDataSource dataSource = new LamborghiniDataSource(lamborghiniConfig);
		new Thread(() -> run(dataSource)).start();
		new Thread(() -> run(dataSource)).start();
		LockSupport.park();
	}
	
	public static void run(LamborghiniDataSource dataSource){
		try {
			Connection connection = dataSource.getConnection();
			log.debug("开始使用{}", connection.toString());
			Thread.sleep(5000);
			log.debug("准备归还{}", connection.toString());
			connection.close();
		} catch (Exception e) {
			log.error("", e);
		}
	}
}