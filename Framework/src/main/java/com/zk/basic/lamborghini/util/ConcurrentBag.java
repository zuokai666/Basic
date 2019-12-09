package com.zk.basic.lamborghini.util;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.zk.basic.lamborghini.util.BagBean.*;

public class ConcurrentBag<T extends BagBean> implements AutoCloseable {
	
	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(ConcurrentBag.class);
	
	private final CopyOnWriteArrayList<T> sharedList;
	private final AtomicInteger waiters;
	private volatile boolean closed;
	private final SynchronousQueue<T> handoffQueue;
	
	public ConcurrentBag() {
		this.handoffQueue = new SynchronousQueue<>(true);
	    this.waiters = new AtomicInteger();
	    this.sharedList = new CopyOnWriteArrayList<>();
	}
	
	public T borrow(long timeout, TimeUnit timeUnit) throws InterruptedException{
		waiters.incrementAndGet();
		try {
			for(T bagEntry : sharedList){
				if(bagEntry.compareAndSet(STATE_NOT_IN_USE, STATE_IN_USE)){
					return bagEntry;
				}
			}
			T bagEntry = handoffQueue.poll(timeout, timeUnit);
			if(bagEntry == null || bagEntry.compareAndSet(STATE_NOT_IN_USE, STATE_IN_USE)){
				return bagEntry;
			}
			return null;
		} finally {
			waiters.decrementAndGet();
		}
	}
	
	/**
	 * 将对象归还
	 * @param bagEntry
	 */
	public void requite(T bagEntry){
		bagEntry.setState(STATE_NOT_IN_USE);
//		if(0 < waiters.get()){//表明有线程在等待
//			if(bagEntry.getState() == STATE_NOT_IN_USE){
//				try {
//					handoffQueue.put(bagEntry);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//			}
//		}
		//2019年12月9日14:34:25 左凯 改动 上述方法有情况下会阻塞
		for(int i=0;0 < waiters.get();i++){
			if(bagEntry.getState() != STATE_NOT_IN_USE || handoffQueue.offer(bagEntry)){//成功的两种情况
				return;
			}else if((i & 0xff) == 0xff){// i>255
				LockSupport.parkNanos(TimeUnit.MICROSECONDS.toNanos(10));
			}else {// i<=255
				Thread.yield();//当前线程让出执行时间
			}
		}
	}
	
	public void add(T bagEntry){
		if(closed){
			throw new IllegalStateException("ConcurrentBag is close.");
		}
		sharedList.add(bagEntry);
//		if(0 < waiters.get()){//表明有线程在等待
//			try {
//				handoffQueue.put(bagEntry);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
		//2019年12月9日14:34:25 左凯 改动 上述方法有情况下会阻塞
		while((0 < waiters.get()) && (!handoffQueue.offer(bagEntry))){
			Thread.yield();//当前线程让出执行时间
		}
	}
	
	/**
	 * 随机删除一个空闲的对象
	 */
	public void randomRemove(){
		for(T bagEntry : sharedList){
			if(bagEntry.compareAndSet(STATE_NOT_IN_USE, STATE_REMOVED)){
				sharedList.remove(bagEntry);
				break;
			}
		}
	}
	
	/**
	 * 不管对象是否正在使用，直接删除
	 * @param bagEntry
	 */
	public void remove(T bagEntry){
		if(bagEntry.compareAndSet(STATE_NOT_IN_USE, STATE_REMOVED) || 
				bagEntry.compareAndSet(STATE_IN_USE, STATE_REMOVED)){
			sharedList.remove(bagEntry);
		}
	}
	
	public int getWaitingThreadCount(){
		return waiters.get();
	}
	
	public int getCountBy(int state){
		int count = 0;
		for(T bagEntry : sharedList){
			if(bagEntry.getState() == state){
				count++;
			}
		}
		return count;
	}
	
	public int size(){
		return sharedList.size();
	}
	
	/**
	 * 这里我认为还应该关闭的彻底
	 */
	@Override
	public void close() throws Exception {
		closed = true;
	}
}