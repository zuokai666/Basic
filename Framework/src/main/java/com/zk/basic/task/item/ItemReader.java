package com.zk.basic.task.item;

public interface ItemReader<T> {

	T read() throws Exception;
}