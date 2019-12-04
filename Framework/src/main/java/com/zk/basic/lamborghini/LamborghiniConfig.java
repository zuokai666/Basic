package com.zk.basic.lamborghini;

/**
 * 数据库连接配置
 * 
 * @author King
 *
 */
public interface LamborghiniConfig {
	
	String getUrl();
	String getDriverClassName();
    String getUser();
    String getPassword();
    
    int getMinIdle();
    int getMaxPoolSize();
    int getConnectionTimeout();
    
    String getPoolName();
}