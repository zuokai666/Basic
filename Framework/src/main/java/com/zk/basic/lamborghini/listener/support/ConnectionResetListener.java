package com.zk.basic.lamborghini.listener.support;

import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zk.basic.lamborghini.LamborghiniDataSource;
import com.zk.basic.lamborghini.listener.LamborghiniConnectionEvent;
import com.zk.basic.lamborghini.listener.LamborghiniEvent;
import com.zk.basic.lamborghini.listener.LamborghiniListener;

/**
 * 连接属性的重置
 * 
 * @author King
 * 
 */
public class ConnectionResetListener implements LamborghiniListener<LamborghiniEvent>{
	
	private static final Logger log = LoggerFactory.getLogger(LamborghiniDataSource.class);
	
	@Override
	public void onLamborghiniEvent(LamborghiniEvent event) {
		if(event instanceof LamborghiniConnectionEvent){
			LamborghiniConnectionEvent _event = (LamborghiniConnectionEvent) event;
			Connection connection = _event.getSource();
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				log.error("", e);
			}
		}
	}
}