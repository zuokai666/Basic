package com.zk.basic.task.bootstrap;

import com.zk.basic.task.config.FileConfig;
import com.zk.basic.task.consume.ThreadPoolConsumer;
import com.zk.basic.task.consume.entity.EntityHandlerFactory;
import com.zk.basic.task.item.support.DefaultFileItemReader;
import com.zk.basic.task.item.support.FileItemReader;

public class FileProcessStep extends PartitionStep{
	
	private FileItemReader fileItemReader;
	
	public FileProcessStep(String name, FileItemReader fileItemReader, int corePoolSize, EntityHandlerFactory entityHandlerFactory) {
		super(name, new ThreadPoolConsumer(corePoolSize, entityHandlerFactory));
		this.fileItemReader = fileItemReader;
	}
	
	public FileProcessStep(String name, FileConfig fileConfig, int corePoolSize, EntityHandlerFactory entityHandlerFactory) {
		this(name, new DefaultFileItemReader(fileConfig), corePoolSize, entityHandlerFactory);
	}
	
	@Override
	protected void doExecute() throws Exception{
		try {
			fileItemReader.open();
			threadPoolConsumer.prestartAllCoreThreads();
			String[] values = null;
			while((values = fileItemReader.read()) != null){
				threadPoolConsumer.consume(values);
			}
			threadPoolConsumer.shutdown();
		} catch (Throwable t) {
			throw t;
		} finally {
			fileItemReader.close();
		}
		threadPoolConsumer.await();
	}
}