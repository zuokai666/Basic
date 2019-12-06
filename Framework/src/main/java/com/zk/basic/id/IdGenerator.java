package com.zk.basic.id;

/**
 * 分布式环境主键生成策略
 * @author King
 *
 */
public interface IdGenerator {
	
	/**
	 * 生成主键，线程安全
	 */
	String generateId();
}