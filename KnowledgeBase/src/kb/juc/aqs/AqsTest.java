package kb.juc.aqs;

import java.util.concurrent.locks.ReentrantLock;

public class AqsTest {

	public static void main(String[] args) {
		ReentrantLock lock = new ReentrantLock();
		lock.lock();
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				lock.lock();
			}
		}).start();
	}
}