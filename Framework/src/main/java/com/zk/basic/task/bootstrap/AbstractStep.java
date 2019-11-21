package com.zk.basic.task.bootstrap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zk.basic.util.StopWatch;

public abstract class AbstractStep implements Step{
	
	private static final Logger log = LoggerFactory.getLogger(AbstractStep.class);
	
	private String name;
	private StopWatch stopWatch;
	
	public AbstractStep(String name) {
		this(name, new StopWatch());
	}
	
	public AbstractStep(String name, StopWatch stopWatch) {
		this.name = name;
		this.stopWatch = stopWatch;
	}
	
	@Override
	public void execute() {
		stopWatch.start(name);
		try {
			doExecute();
		} catch (Throwable t) {
			log.error("step["+name+"]执行失败", t);
		}
		stopWatch.stop();
		log.debug("{}", stopWatch.prettyPrint());
	}
	
	protected abstract void doExecute() throws Exception;
}