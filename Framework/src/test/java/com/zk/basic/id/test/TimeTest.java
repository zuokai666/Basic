package com.zk.basic.id.test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeTest {

	public static void main(String[] args) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		System.err.println(dateFormat.format(new Date()));
	}
}