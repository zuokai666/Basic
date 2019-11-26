package com.zk.basic.task.bootstrap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zk.basic.task.config.FileConfig;
import com.zk.basic.task.consume.ThreadPoolConsumer;
import com.zk.basic.task.consume.entity.EntityHandlerFactory;
import com.zk.basic.task.process.DefaultFileItemReader;
import com.zk.basic.task.process.FileItemReader;

public class FileProcessStep extends PartitionStep{
	
	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(FileProcessStep.class);
	
	private FileItemReader fileItemReader;
	private ThreadPoolConsumer threadPoolConsumer;
	
	public FileProcessStep(String name, FileItemReader fileItemReader, int corePoolSize, EntityHandlerFactory entityHandlerFactory) {
		super(name);
		this.fileItemReader = fileItemReader;
		this.threadPoolConsumer = new ThreadPoolConsumer(corePoolSize, entityHandlerFactory);
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