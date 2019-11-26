package com.zk.basic.task.bootstrap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zk.basic.util.StopWatch;

/**
 * 步骤的抽象父类，添加了计时方法
 * 
 * @author King
 *
 */
public abstract class AbstractStep implements Step{
	
	private static final Logger log = LoggerFactory.getLogger(AbstractStep.class);
	
	private String name;
	
	public AbstractStep(String name) {
		this.name = name;
	}
	
	@Override
	public void execute() {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start(name);
		try {
			doExecute();
			stopWatch.normalStop();
		} catch (Throwable t) {
			log.error("", t);
			stopWatch.exceptionStop(t);
		}
	}
	
	protected abstract void doExecute() throws Exception;
}