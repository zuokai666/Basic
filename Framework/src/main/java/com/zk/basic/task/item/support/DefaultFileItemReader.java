package com.zk.basic.task.item.support;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zk.basic.task.config.FileConfig;

public class DefaultFileItemReader implements FileItemReader{
	
	private static final Logger log = LoggerFactory.getLogger(DefaultFileItemReader.class);
	
	private BufferedReader bufferedReader;
	private FileConfig fileConfig;
	
	public DefaultFileItemReader(FileConfig fileConfig) {
		this.fileConfig = fileConfig;
	}
	
	@Override
	public void open() throws IOException {
		bufferedReader = new BufferedReader(new InputStreamReader(
				new FileInputStream(fileConfig.getFilePath()), fileConfig.getFileCharset()));
		if(fileConfig.shouldSkip()){
			for(int i=0;i<fileConfig.getSkipRows();i++){
				bufferedReader.readLine();
			}
			log.debug("读取[{}]文件跳过[{}]行", fileConfig.getFilePath(), fileConfig.getSkipRows());
		}
	}
	
	@Override
	public String[] read() throws Exception {
		String line = bufferedReader.readLine();
		if(line == null){
			return null;
		}
		String[] values = line.split(fileConfig.getFileSperator(), -1);
		return values;
	}
	
	@Override
	public void close() throws IOException {
		if(bufferedReader != null){
			bufferedReader.close();
		}
	}
}