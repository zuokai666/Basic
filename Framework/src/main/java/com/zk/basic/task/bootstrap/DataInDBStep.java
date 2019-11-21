package com.zk.basic.task.bootstrap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zk.basic.task.process.FileProcessor;
import com.zk.basic.task.process.InsertDBProcessor;
import com.zk.basic.util.StopWatch;

public class DataInDBStep extends AbstractStep{
	
	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(DataInDBStep.class);
	
	private FileProcessor fileProcessor;
	private InsertDBProcessor insertDBProcessor;
	
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
			insertDBProcessor.start();
			String[] values = null;
			while((values = fileProcessor.read()) != null){
				insertDBProcessor.handle(values);
			}
		} finally {
			fileProcessor.close();
			insertDBProcessor.end();
		}
	}
}