package com.zk.basic.lamborghini.util;

public interface BagBean {
	
	int STATE_NOT_IN_USE = 0;
	int STATE_IN_USE = 1;
	int STATE_REMOVED = 2;
	
	boolean compareAndSet(int expectState, int newState);
	
	void setState(int newState);
	
	int getState();
}