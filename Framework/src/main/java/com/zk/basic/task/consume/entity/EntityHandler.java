package com.zk.basic.task.consume.entity;

import com.zk.basic.common.Lifecycle;

/**
 * 实体处理器
 * 
 * @author King
 *
 */
public interface EntityHandler extends Lifecycle{
	
	void handle(Object entity);
}