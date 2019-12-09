package com.zk.basic.id;

/**
 * 根据数据库表名来获取主键，因为表如果不同，就不会冲突，所以减小主键生成的范围到表级别
 * 
 * @author King
 * 
 */
public interface IdGenerators extends IdGenerator{
	
	/**
	 * 根据表名来生成不同的主键
	 * @param tableName
	 * @return
	 */
	String generateId(String tableName);
	
	/**
	 * 2019年12月9日12:46:39 增加此方法，对外公开对应表名使用的对应的主键生成器
	 * 
	 * @param tableName
	 * @return
	 */
	IdGenerator getIdGenerator(String tableName);
}