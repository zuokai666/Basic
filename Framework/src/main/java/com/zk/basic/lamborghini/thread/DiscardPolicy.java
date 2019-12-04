package com.zk.basic.lamborghini.thread;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DiscardPolicy implements RejectedExecutionHandler{
	
	private static final Logger log = LoggerFactory.getLogger(DiscardPolicy.class);
	
	@Override
	public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
		log.info("[{}]执行任务丢弃策略,任务为[{}]", executor, r);
	}
}