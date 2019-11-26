package com.zk.basic.task.consume;

/**
 * 多线程消费者
 * 
 * @author King
 *
 */
public interface Consumer {
	
	/**
	 * 消费对象
	 * @param object
	 */
	void consume(Object object);
}