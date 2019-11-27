package com.zk.basic.task.bootstrap;

import com.zk.basic.task.consume.ThreadPoolConsumer;

public abstract class PartitionStep extends AbstractStep{
	
	protected ThreadPoolConsumer threadPoolConsumer;
	
	public PartitionStep(String name, ThreadPoolConsumer threadPoolConsumer) {
		super(name);
		this.threadPoolConsumer = threadPoolConsumer;
	}
}