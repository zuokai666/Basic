package com.zk.basic.task.bootstrap;

import java.util.concurrent.Executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zk.basic.id.IdGenerator;
import com.zk.basic.id.impl.DefaultIdGeneratorProperties;
import com.zk.basic.id.impl.SimpleIdGenerator;
import com.zk.basic.task.executor.SimpleExecutor;
import com.zk.basic.task.process.FileProcessor;
import com.zk.basic.task.process.InsertDBProcessor;
import com.zk.basic.util.StopWatch;

public class DataInDBStep extends AbstractStep{
	
	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(DataInDBStep.class);
	
	private FileProcessor fileProcessor;
	private InsertDBProcessor insertDBProcessor;
	private Executor executor = new SimpleExecutor();
	
	public DataInDBStep(String name, FileProcessor fileProcessor, InsertDBProcessor insertDBProcessor) {
		super(name);
		this.fileProcessor = fileProcessor;
		this.insertDBProcessor = insertDBProcessor;
	}
	
	public DataInDBStep(String name, StopWatch stopWatch, FileProcessor fileProcessor) {
		super(name, stopWatch);
		this.fileProcessor = fileProcessor;
	}
	
	@Override
	protected void doExecute() throws Exception{
		try {
			fileProcessor.open();
			IdGenerator idGenerator = createIdGenerator();
			insertDBProcessor.start(executor, idGenerator);
			String[] values = null;
			while((values = fileProcessor.read()) != null){
				insertDBProcessor.handle(values);
			}
		} catch (Throwable t) {
			throw t;
		} finally {
			fileProcessor.close();
			insertDBProcessor.end();
		}
	}
	
	protected IdGenerator createIdGenerator(){
		String dataCenterId = DefaultIdGeneratorProperties.dataCenterId;
		String machineId = DefaultIdGeneratorProperties.machineId;
		String processId = DefaultIdGeneratorProperties.processId;
		IdGenerator defaultIdGenerator = new SimpleIdGenerator(dataCenterId, machineId, processId);
		return defaultIdGenerator;
	}
}