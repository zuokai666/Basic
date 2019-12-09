package com.zk.basic.lamborghini.listener;

import java.sql.Connection;

public class LamborghiniConnectionEvent extends LamborghiniEvent{
	
	private static final long serialVersionUID = 1593956457526027051L;
	
	public LamborghiniConnectionEvent(Connection source) {
		super(source);
	}
	
	@Override
	public Connection getSource() {
		return (Connection) super.getSource();
	}
}