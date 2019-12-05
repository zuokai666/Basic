package com.zk.concurrent.test;

import java.util.concurrent.Semaphore;

public class SemaphoreTest {
	
	public static void main(String[] args) {
		Semaphore semaphore = new Semaphore(1, true);
		new Thread(() -> run(semaphore)).start();
		new Thread(() -> run(semaphore)).start();
	}
	
	public static void run(Semaphore semaphore){
		semaphore.acquireUninterruptibly();
		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.err.println(1);
		semaphore.release();
	}
}