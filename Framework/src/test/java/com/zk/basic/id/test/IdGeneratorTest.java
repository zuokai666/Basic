package com.zk.basic.id.test;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zk.basic.beans.factory.Summer;
import com.zk.basic.id.IdGenerators;
import com.zk.basic.test.Configuration;

public class IdGeneratorTest {
	
	private static final Logger log = LoggerFactory.getLogger(IdGeneratorTest.class);
	
	public static void main(String[] args) {
		new Configuration().init();
//		while(true){
//			String id = defaultIdGenerator.generateId();
//			log.info("生成的主键:[{}]", id);
//		}
		
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(50, 50, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
		for(int i=0;i<1000;i++){
			threadPoolExecutor.execute(() -> run());
		}
		threadPoolExecutor.shutdown();
	}
	
	public static void run(){
		String id = Summer.rain().getBean(IdGenerators.class).generateId("");
		log.info(id);
	}
}