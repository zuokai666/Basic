package com.zk.basic.task.consume;

public interface ConsumerService extends Consumer{
	
	/**
	 * 停止接收外部对象
	 */
	void shutdown();
	
	/**
	 * 
	 * @return 是否已经停止
	 */
	boolean isShutdown();
	
	/**
	 * 等待所有线程是否消费完毕
	 * @throws InterruptedException
	 */
	void await() throws InterruptedException;
}