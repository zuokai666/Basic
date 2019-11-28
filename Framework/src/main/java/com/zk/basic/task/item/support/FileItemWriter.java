package com.zk.basic.task.item.support;

import java.io.IOException;

import com.zk.basic.task.item.ItemWriter;

public interface FileItemWriter extends ItemWriter<String>{
	
	void open() throws IOException;
	
	void close() throws IOException;
}