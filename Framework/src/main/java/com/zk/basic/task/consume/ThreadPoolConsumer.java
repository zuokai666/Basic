package com.zk.basic.task.consume;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zk.basic.task.consume.entity.EntityHandler;
import com.zk.basic.task.consume.entity.EntityHandlerFactory;

/**
 * 多线程消费者
 * @author King
 * 
 */
public class ThreadPoolConsumer implements ConsumerService{
	
	private static final Logger log = LoggerFactory.getLogger(ThreadPoolConsumer.class);
	
	private static final int RUNNING = 0;//运行状态
	private static final int SHUTDOWN = 1;//停止接收对象
	
	private final AtomicInteger state = new AtomicInteger(RUNNING);//初始化默认运行
	
	private final BlockingQueue<Object> workQueue;
	private volatile ThreadFactory threadFactory = new DefaultThreadFactory();
	private volatile int corePoolSize;
	private final CountDownLatch countDownLatch;
	private final EntityHandlerFactory entityHandlerFactory;
	private final Object endFlag = new Object();
	
	public ThreadPoolConsumer(int corePoolSize, EntityHandlerFactory entityHandlerFactory) {
		this.corePoolSize = corePoolSize;
		this.entityHandlerFactory = entityHandlerFactory;
		this.workQueue = new ArrayBlockingQueue<>(corePoolSize * 10);
		this.countDownLatch = new CountDownLatch(corePoolSize);
	}
	
	public void prestartAllCoreThreads() {
		for(int i=0;i<corePoolSize;i++){
			Thread thread = threadFactory.newThread(new Worker(entityHandlerFactory.createEntityHandler()));
			thread.start();
		}
	}
	
	//幂等操作
	@Override
	public void shutdown() {
		for(;;){
			int c = state.get();
			if(SHUTDOWN <= c){//重复操作
				break;
			}else if(state.compareAndSet(c, SHUTDOWN)){
				putcorePoolSizeEndFlag();//只会执行一次
				break;
			}
		}
	}
	
	//在队列末尾加入结束标志
	private void putcorePoolSizeEndFlag(){
		for(int i=0;i<corePoolSize;i++){
			useBlockConsume(endFlag);
		}
	}
	
	@Override
	public boolean isShutdown() {
		return state.get() == SHUTDOWN;
	}
	
	@Override
	public void await() throws InterruptedException {
		countDownLatch.await();
	}
	
	@Override
	public void consume(Object object) {
		if(isShutdown()){
			throw new RuntimeException("拒绝添加对象");
		}else {
			useBlockConsume(object);
		}
	}
	
	public void useBlockConsume(Object object) {
		try {
			workQueue.put(object);
		} catch (InterruptedException e) {
			log.error("", e);
		}
	}
	
	private final class Worker implements Runnable{
		
		private EntityHandler entityHandler;
		
		public Worker(EntityHandler entityHandler) {
			this.entityHandler = entityHandler;
		}
		
		@Override
		public void run() {
			try {
				entityHandler.start();
				Object entity = null;
				while((entity = workQueue.take()) != endFlag){
					entityHandler.handle(entity);
				}
			} catch (Throwable t) {
				log.error("", t);
			} finally {
				try {
					entityHandler.stop();//必须执行 2019年12月9日13:01:30 左凯 改动
				} catch (Exception e) {
					log.error("", e);
				}
				countDownLatch.countDown();
			}
		}
	}
}