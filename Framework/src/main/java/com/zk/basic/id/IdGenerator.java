package com.zk.basic.id;

/**
 * <p>分布式环境主键生成策略：采用时间到毫秒+数据中心号+机器号+进程号+递增序列</p>
 * <p>如果当毫秒相同，序列号越界，加以自旋，等待该毫秒通过</p>
 * <p>理论上，一秒一个java进程最多产生1000*10000=1000W的唯一序列</p>
 * 
 * @author King
 *
 */
public interface IdGenerator {
	
	/**
	 * 生成主键，线程安全
	 */
	String generateId();
}