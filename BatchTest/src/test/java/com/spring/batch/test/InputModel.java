package com.spring.batch.test;

public class InputModel {

	private int i;
	
	public InputModel(int i) {
		this.i = i;
	}
	
	@Override
	public String toString() {
		return "InputModel " + i;
	}
}
