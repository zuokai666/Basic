package com.zk.basic.id.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zk.basic.id.IdGenerator;

/**
 * 主键生成策略的默认实现：synchronized + CAS
 * 
 * @author King
 * 
 */
public class DefaultIdGenerator implements IdGenerator{
	
	private static final Logger log = LoggerFactory.getLogger(DefaultIdGenerator.class);
	
	private String dataCenterId;
	private String machineId;
	private String processId;
	private String cacheContent;
	
	private AtomicInteger serialAI = new AtomicInteger(SERIAL_MIN);
	private long lastTimeMillis = System.currentTimeMillis();//初始化
	private Object lock = new Object();
	
	/**
	 * 
	 * @param dataCenterId 数据中心
	 * @param machineId 机器
	 * @param processId 进程
	 */
	public DefaultIdGenerator(String dataCenterId, String machineId, String processId) {
		this.dataCenterId = dataCenterId;
		this.machineId = machineId;
		this.processId = processId;
		this.cacheContent = this.dataCenterId + this.machineId + this.processId;
	}
	
	@Override
	public String generateId() {
		long timeNum = 0;
		synchronized (lock) {
			timeNum = System.currentTimeMillis();
			if(lastTimeMillis != timeNum){//毫秒不相同，重置时间与序列号
				serialAI.set(SERIAL_MIN);
				lastTimeMillis = timeNum;
			}else if(SERIAL_MAX < serialAI.get()){//毫秒相同，序列号越界
				do {
					timeNum = System.currentTimeMillis();
					log.info("spin");
				} while (lastTimeMillis == timeNum);//自旋
				serialAI.set(SERIAL_MIN);
				lastTimeMillis = timeNum;
			}
		}
		
		String timeSeq = new SimpleDateFormat(TIME_PATTERN).format(new Date(timeNum));
		int seq = serialAI.getAndIncrement();
		String serialSeq = seq + "";
		
		StringBuilder sb = new StringBuilder(TOTAL_LENGTH);
		sb.append(timeSeq);
		sb.append(cacheContent);
		sb.append(serialSeq);
		return sb.toString();
	}
}