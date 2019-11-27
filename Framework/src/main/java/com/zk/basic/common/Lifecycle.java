package com.zk.basic.common;

/**
 * 组件生命周期方法的通用接口
 * 当状态改变时，生命周期事件将会被触发
 * 
 * @author king
 * @date 2019-05-02 16:22:36
 * 
 */
public interface Lifecycle {
	
	void start() throws Exception;
	
    void stop() throws Exception;
}