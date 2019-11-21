package com.zk.basic.id.test;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.zk.basic.id.IdGenerator;
import com.zk.basic.id.impl.DefaultIdGenerator;
import com.zk.basic.id.impl.DefaultIdGeneratorProperties;

public class IdGeneratorTest {
	
	private static final Logger log = LoggerFactory.getLogger(IdGeneratorTest.class);
	
	public static void main(String[] args) {
		String dataCenterId = DefaultIdGeneratorProperties.dataCenterId;
		String machineId = DefaultIdGeneratorProperties.machineId;
		String processId = DefaultIdGeneratorProperties.processId;
		IdGenerator defaultIdGenerator = new DefaultIdGenerator(dataCenterId, machineId, processId);
		
//		while(true){
//			String id = defaultIdGenerator.generateId();
//			log.info("生成的主键:[{}]", id);
//		}
		
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(50, 50, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
		for(int i=0;i<10000;i++){
			threadPoolExecutor.execute(() -> run(defaultIdGenerator));
		}
		threadPoolExecutor.shutdown();
	}
	
	public static void run(IdGenerator defaultIdGenerator){
		String id = defaultIdGenerator.generateId();
		log.info("生成的主键:[{}]", id);
	}
}