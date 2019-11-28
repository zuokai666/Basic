package com.zk.basic.task.config;

public interface FileReaderConfig extends FileConfig{

	/**
	 * 是否应该跳过行，例如标题等等
	 * @return
	 */
	boolean shouldSkip();
	
	/**
	 * 应该跳过多少行
	 * @return
	 */
	int getSkipRows();
}