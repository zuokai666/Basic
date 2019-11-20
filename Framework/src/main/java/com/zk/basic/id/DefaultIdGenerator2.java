package com.zk.basic.id;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 主键生成策略的默认实现
 * 
 * @author King
 *
 */
public class DefaultIdGenerator2 implements IdGenerator{
	
	private String dataCenterId;
	private String machineId;
	private String processId;
	
	private AtomicInteger serialAI = new AtomicInteger(1000);
	private volatile String lastTime;
	private Object lock = new Object();
	
	/**
	 * 
	 * @param dataCenterId 数据中心
	 * @param machineId 机器
	 * @param processId 进程
	 */
	public DefaultIdGenerator2(String dataCenterId, String machineId, String processId) {
		this.dataCenterId = dataCenterId;
		this.machineId = machineId;
		this.processId = processId;
	}
	
	@Override
	public String generateId() {
		String timeSeq = new SimpleDateFormat(TIME_PATTERN).format(new Date());
		synchronized (lock) {
			if(!timeSeq.equals(lastTime)){//毫秒不相同，重置序列号
				serialAI.set(1000);
				lastTime = timeSeq;
			}
		}
		int seq = serialAI.getAndIncrement();
		
		String dataCenterSeq = dataCenterId;
		String machineSeq = machineId;
		String processSeq = processId;
		String serialSeq = seq + "";
		
		StringBuilder sb = new StringBuilder(TOTAL_LENGTH);
		sb.append(timeSeq);
		sb.append(dataCenterSeq);
		sb.append(machineSeq);
		sb.append(processSeq);
		sb.append(serialSeq);
		return sb.toString();
	}
}