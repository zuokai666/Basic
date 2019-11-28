package com.zk.basic.task.config;

/**
 * 批量文件配置
 * 
 * @author King
 *
 */
public interface FileConfig {
	
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