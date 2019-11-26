package com.zk.basic.task.consume.entity.support;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zk.basic.task.consume.entity.EntityHandler;

public class PrintEntityHandler implements EntityHandler{
	
	private static final Logger log = LoggerFactory.getLogger(PrintEntityHandler.class);
	
	@Override
	public void start() {
	}
	
	@Override
	public void stop() {
	}
	
	@Override
	public void handle(Object entity) {
		if(entity instanceof String[]){
			log.debug(Arrays.toString((String[])entity));
		}
	}
}