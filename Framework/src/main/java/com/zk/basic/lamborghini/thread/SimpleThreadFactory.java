package com.zk.basic.lamborghini.thread;

import java.util.concurrent.ThreadFactory;

public class SimpleThreadFactory implements ThreadFactory {
	
    private final String threadName;
    private final boolean daemon;
    
    public SimpleThreadFactory(String threadName, boolean daemon){
    	this.threadName = threadName;
    	this.daemon = daemon;
    }
    
    @Override
    public Thread newThread(Runnable r) {
    	Thread thread = new Thread(r, threadName);
    	thread.setDaemon(daemon);
    	return thread;
    }
}