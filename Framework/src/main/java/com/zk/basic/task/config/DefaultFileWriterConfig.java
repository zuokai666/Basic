package com.zk.basic.task.config;

public class DefaultFileWriterConfig implements FileWriterConfig{
	
	private String fileCharset;
	private String filePath;
	private String fileSperator;
	
	public DefaultFileWriterConfig(String fileCharset, String filePath, String fileSperator) {
		this.fileCharset = fileCharset;
		this.filePath = filePath;
		this.fileSperator = fileSperator;
	}
	
	@Override
	public String getFileCharset() {
		return fileCharset;
	}

	@Override
	public String getFilePath() {
		return filePath;
	}
	
	@Override
	public String getFileSperator() {
		return fileSperator;
	}
}