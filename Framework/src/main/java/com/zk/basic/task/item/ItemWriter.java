package com.zk.basic.task.item;

public interface ItemWriter<T> {
	
	void write(T content) throws Exception;
}