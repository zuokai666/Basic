package com.zk.basic.task.config;

/**
 * 批量文件配置
 * 
 * @author King
 *
 */
public interface FileConfig {
	
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
	
	/**
	 * 文件字符编码
	 * @return
	 */
	String getFileCharset();
	
	/**
	 * 
	 * @return 文件路径
	 */
	String getFilePath();
	
	/**
	 * 
	 * @return 分隔符
	 */
	String getFileSperator();
}