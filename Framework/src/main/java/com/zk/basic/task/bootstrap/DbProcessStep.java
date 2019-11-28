package com.zk.basic.task.bootstrap;

import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zk.basic.task.config.FileWriterConfig;
import com.zk.basic.task.config.QueryDbConfig;
import com.zk.basic.task.consume.ThreadPoolConsumer;
import com.zk.basic.task.consume.entity.EntityHandlerFactory;
import com.zk.basic.task.item.support.DbKeyItemReader;
import com.zk.basic.task.item.support.DefaultDbKeyItemReader;
import com.zk.basic.util.FileMerger;

/**
 * 数据库数据导入文件_步骤
 * @author King
 *
 */
public class DbProcessStep extends PartitionStep{
	
	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(DbProcessStep.class);
	
	@SuppressWarnings("unused")
	private QueryDbConfig queryDbConfig;
	private FileWriterConfig fileWriterConfig;
	private DbKeyItemReader dbKeyItemReader;
	
	public DbProcessStep(String name, QueryDbConfig queryDbConfig, 
			FileWriterConfig fileWriterConfig, EntityHandlerFactory entityHandlerFactory){
		super(name, new ThreadPoolConsumer(queryDbConfig.getThreadCount(), entityHandlerFactory));
		this.dbKeyItemReader = new DefaultDbKeyItemReader(queryDbConfig);
		this.fileWriterConfig = fileWriterConfig;
		this.queryDbConfig = queryDbConfig;
	}
	
	@Override
	protected void doExecute() throws Exception {
		try {
			FileMerger.newFile(fileWriterConfig.getFilePath());//创建文件夹用来存在临时数据
			threadPoolConsumer.prestartAllCoreThreads();
			List<String> list = dbKeyItemReader.read();
			Iterator<String> iterator = list.iterator();
			while(iterator.hasNext()){
				threadPoolConsumer.consume(iterator.next());
			}
			threadPoolConsumer.shutdown();
		} catch (Throwable t) {
			throw t;
		}
		threadPoolConsumer.await();
		FileMerger.merge(fileWriterConfig.getFilePath());//创建文件夹用来存在临时数据
	}
}