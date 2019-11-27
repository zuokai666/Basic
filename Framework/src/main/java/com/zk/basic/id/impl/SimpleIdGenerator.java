package com.zk.basic.id.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zk.basic.id.IdGenerator;
import com.zk.basic.task.config.IdGenerateConfig;

/**
 * 简单的主键生成策略的默认实现，编程简单，效率可能比较低下
 * 
 * @author King
 *
 */
public class SimpleIdGenerator implements IdGenerator{
	
	private static final Logger log = LoggerFactory.getLogger(SimpleIdGenerator.class);
	
	private String dataCenterId;
	private String machineId;
	private String processId;
	private String cacheContent;
	
	private int serialNum = SERIAL_MIN;
	private long lastTimeMillis = System.currentTimeMillis();//初始化
	
	/**
	 * 
	 * @param dataCenterId 数据中心
	 * @param machineId 机器
	 * @param processId 进程
	 */
	public SimpleIdGenerator(String dataCenterId, String machineId, String processId) {
		this.dataCenterId = dataCenterId;
		this.machineId = machineId;
		this.processId = processId;
		this.cacheContent = this.dataCenterId + this.machineId + this.processId;
	}
	
	public SimpleIdGenerator(IdGenerateConfig idGenerateConfig) {
		this(idGenerateConfig.getDataCenterId(), idGenerateConfig.getMachineId(), idGenerateConfig.getProcessId());
	}
	
	@Override
	public synchronized String generateId() {
		long timeNum = System.currentTimeMillis();
		if(lastTimeMillis != timeNum){//毫秒不相同，重置时间与序列号
			serialNum = SERIAL_MIN;
			lastTimeMillis = timeNum;
		}else if(SERIAL_MAX < serialNum){//毫秒相同，序列号越界
			do {
				timeNum = System.currentTimeMillis();
				log.info("spin");
			} while (lastTimeMillis == timeNum);//自旋
			serialNum = SERIAL_MIN;
			lastTimeMillis = timeNum;
		}
		
		int seq = serialNum;
		serialNum++;
		String timeSeq = new SimpleDateFormat(TIME_PATTERN).format(new Date(timeNum));
		String serialSeq = seq + "";
		
		StringBuilder sb = new StringBuilder(TOTAL_LENGTH);
		sb.append(timeSeq);
		sb.append(cacheContent);
		sb.append(serialSeq);
		return sb.toString();
	}
}