package com.zk.basic.task.exception;

public class StepExecuteException extends RuntimeException{
	
	private static final long serialVersionUID = -4254224776632973866L;
	
	public StepExecuteException(String message) {
        super(message);
    }
}