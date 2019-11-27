package com.zk.basic.task.item.support;

import java.io.IOException;

import com.zk.basic.task.item.ItemReader;

public interface FileItemReader extends ItemReader<String[]>{
	
	void open() throws IOException;
	
	void close() throws IOException;
}