package com.zk.basic.task.item.support;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import com.zk.basic.task.config.FileWriterConfig;

public class DefaultFileItemWriter implements FileItemWriter{
	
	private BufferedWriter bufferedWriter;
	private FileWriterConfig fileConfig;
	
	public DefaultFileItemWriter(FileWriterConfig fileConfig) {
		this.fileConfig = fileConfig;
	}
	
	@Override
	public void open() throws IOException {
		String name = fileConfig.getFilePath() + FileWriterConfig.FILE_END + 
				File.separator + Thread.currentThread().getName();
		File file = new File(name);
		if(file.exists()){
			file.delete();
		}
		file.createNewFile();
		bufferedWriter = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(
						name), fileConfig.getFileCharset()));
	}
	
	@Override
	public void write(String content) throws Exception {
		bufferedWriter.write(content);
	}
	
	@Override
	public void close() throws IOException {
		if(bufferedWriter != null){
			bufferedWriter.close();
		}
	}
}