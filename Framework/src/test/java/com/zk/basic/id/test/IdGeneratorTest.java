package com.zk.basic.id.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zk.basic.id.DefaultIdGenerator;
import com.zk.basic.id.DefaultIdGenerator2;
import com.zk.basic.id.DefaultIdGeneratorProperties;
import com.zk.basic.id.IdGenerator;

public class IdGeneratorTest {
	
	private static final Logger log = LoggerFactory.getLogger(IdGeneratorTest.class);
	
	public static void main(String[] args) {
		String dataCenterId = DefaultIdGeneratorProperties.dataCenterId;
		String machineId = DefaultIdGeneratorProperties.machineId;
		String processId = DefaultIdGeneratorProperties.processId;
		IdGenerator defaultIdGenerator = new DefaultIdGenerator2(dataCenterId, machineId, processId);
		
		for(int i=0;i<100;i++){
			String id = defaultIdGenerator.generateId();
			log.info("生成的主键:[{}]", id);
		}
	}
}