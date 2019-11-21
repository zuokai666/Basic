package com.zk.basic.id.test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeTest {
	
	public static void main(String[] args) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		System.err.println(dateFormat.format(new Date(1000)));
		
		while(true){
			System.err.println(System.currentTimeMillis());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}