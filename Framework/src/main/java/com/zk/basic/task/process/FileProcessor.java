package com.zk.basic.task.process;

import java.io.IOException;

public interface FileProcessor {
	
	void open() throws IOException;
	
	String[] read() throws Exception;
	
	void close() throws IOException;
}