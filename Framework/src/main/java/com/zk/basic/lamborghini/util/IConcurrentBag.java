package com.zk.basic.lamborghini.util;

import java.util.concurrent.TimeUnit;

/**
 * 对ConcurrentBag抽象接口，仅做学习使用
 * 
 * @author King
 *
 */
@Deprecated
public interface IConcurrentBag<T> {
	
	/**
	 * 向容器中添加元素，可以被检测状态的定时器调用
	 */
	void add(T t);
	
	/**
	 * 从容器中删除元素
	 */
	void remove(T t);
	
	/**
	 * 元素在容器内，只是将属性设置为已经借出
	 * 
	 * @return 如果超时等待返回null
	 */
	T borrow(long timeout, TimeUnit timeUnit) throws InterruptedException;
	
	/**
	 * 归还元素，只是将属性设置为已经归还
	 */
	void requite(T t);
}