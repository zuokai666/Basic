package com.zk.basic.lamborghini.listener;

import java.sql.Connection;
import java.util.EventObject;

public abstract class LamborghiniEvent extends EventObject{
	
	private static final long serialVersionUID = 8415058073005242677L;

	public LamborghiniEvent(Connection source) {
		super(source);
	}
}