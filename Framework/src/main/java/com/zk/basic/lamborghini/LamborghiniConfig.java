package com.zk.basic.lamborghini;

/**
 * 数据库连接配置，向内传递全部使用接口，使得其他组件依赖接口，降低耦合性
 * 
 * @author King
 * 
 */
public interface LamborghiniConfig {
	
	String getUrl();
	String getDriverClassName();
    String getUser();
    String getPassword();
    
    /**
     * <p>数据库连接池最小空闲数</p>
     * <p>当连接数小于该配置时，定时任务应该增加连接使其达到该配置</p>
     * <p>当连接数大于该配置时，定时任务应该减少连接使其达到该配置</p>
     * 
     * @return
     */
    int getMinIdle();
    
    /**
     * <p>数据库连接池最大连接数</p>
     * @return
     */
    int getMaxPoolSize();
    
    /**
     * 当没有空闲连接时，等待连接最长时间
     * @return
     */
    int getConnectionTimeout();
}