package com.zk.basic.beans.exception;

public class UnsupportedTypeException extends RuntimeException{
	
	private static final long serialVersionUID = 3299275481453912695L;
	
	public UnsupportedTypeException(String name) {
		super("Bean named '" + name + "' no supported.");
	}
}