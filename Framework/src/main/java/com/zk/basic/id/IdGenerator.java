package com.zk.basic.id;

/**
 * 分布式环境主键生成策略
 * @author King
 *
 */
public interface IdGenerator {
	
	/**
	 * 时间段长度，格式yyyyMMddHHmmssSSS
	 */
	int TIME_LENGTH = 17;
	String TIME_PATTERN = "yyyyMMddHHmmssSSS";
	
	/**
	 * 数据中心长度，最多100个数据中心
	 */
	int DATACENTER_LENGTH = 2;
	
	/**
	 * 机器长度，每个数据中心最多1000台服务器
	 */
	int MACHINE_LENGTH = 3;
	
	/**
	 * 进程长度，每台服务器最多10个进程
	 */
	int PROCESS_LENGTH = 1;
	
	/**
	 * 自增序列长度，每个进程每毫秒最多10000个序列
	 * 如果seq从1000开始，就不会涉及到缺位的问题，这样每个进程每毫秒最多9000个序列
	 */
	int SERIAL_LENGTH = 4;
	
	int TOTAL_LENGTH = TIME_LENGTH + DATACENTER_LENGTH + MACHINE_LENGTH + PROCESS_LENGTH + SERIAL_LENGTH;
	
	/**
	 * 生成主键，线程安全
	 */
	String generateId();
}