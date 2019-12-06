package com.zk.basic.id;

/**
 * 根据数据库表名来获取主键，因为表如果不同，就不会冲突，所以减小主键生成的范围到表级别
 * 
 * @author King
 * 
 */
public interface IdGenerators extends IdGenerator{
	
	String generateId(String tableName);
}