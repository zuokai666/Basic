package com.zk.basic.lamborghini.listener;

import java.util.EventListener;

public interface LamborghiniListener<E extends LamborghiniEvent> extends EventListener{
	
	/**
	 * 处理事件
	 * 
	 * @param event
	 */
	void onLamborghiniEvent(E event);
}