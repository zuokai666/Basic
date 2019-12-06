package com.zk.basic.dbcp.test;

import java.sql.Connection;
import java.util.Random;
import java.util.concurrent.locks.LockSupport;

import javax.sql.DataSource;

import org.slf4j.LoggerFactory;

import com.zk.basic.beans.factory.Summer;
import com.zk.basic.test.Configuration;

public class LamborghiniCPTest {
	
	private static final org.slf4j.Logger log = LoggerFactory.getLogger(LamborghiniCPTest.class);
	
	public static void main(String[] args) {
		new Configuration().init();
		DataSource dataSource = Summer.rain().getBean(DataSource.class);
		new Thread(() -> run(dataSource)).start();
		new Thread(() -> run(dataSource)).start();
		LockSupport.park();
	}
	
	public static void whileRun(DataSource dataSource){
		while(true){
			run(dataSource);
		}
	}
	
	public static void run(DataSource dataSource){
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
	
	public static void run1(DataSource dataSource){
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