package com.zk.basic.beans.exception;

public class NoSuchBeanException extends RuntimeException{
	
	private static final long serialVersionUID = 3299275481453912695L;
	
	public NoSuchBeanException(String name) {
		super("No bean named '" + name + "' available");
	}
}