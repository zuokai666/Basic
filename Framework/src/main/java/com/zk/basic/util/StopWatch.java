package com.zk.basic.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 简单计时器
 * 
 * @author King
 *
 */
public class StopWatch {
	
	private static final Logger log = LoggerFactory.getLogger(StopWatch.class);
	
	private long startTimeMillis;
	private String currentTaskName;
	
	public void start(String taskName) {
		this.currentTaskName = taskName;
		this.startTimeMillis = System.currentTimeMillis();
		log.debug("step[{}]开始执行", taskName);
	}
	
	public void normalStop() {
		long lastTime = System.currentTimeMillis() - this.startTimeMillis;
		log.debug("step[{}]正常结束，花费时间[{}]ms", currentTaskName, lastTime);
	}
	
	public void exceptionStop(Throwable t) {
		long lastTime = System.currentTimeMillis() - this.startTimeMillis;
		log.error("step[{}]异常结束，花费时间[{}]ms，异常为[{}]", currentTaskName, lastTime, t.getMessage());
	}
}