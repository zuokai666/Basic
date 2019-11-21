package com.zk.basic.task.config;

public class DefaultFileConfig implements FileConfig{
	
	private boolean shouldSkip;
	private int skipRows;
	private String fileCharset;
	private String filePath;
	private String fileSperator;
	
	public DefaultFileConfig(boolean shouldSkip, int skipRows, String fileCharset, String filePath, String fileSperator) {
		this.shouldSkip = shouldSkip;
		this.skipRows = skipRows;
		this.fileCharset = fileCharset;
		this.filePath = filePath;
		this.fileSperator = fileSperator;
	}
	
	@Override
	public boolean shouldSkip() {
		return shouldSkip;
	}

	@Override
	public int getSkipRows() {
		return skipRows;
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