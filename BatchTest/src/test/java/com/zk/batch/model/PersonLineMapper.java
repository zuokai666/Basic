package com.zk.batch.model;

import org.springframework.batch.item.file.LineMapper;

public class PersonLineMapper implements LineMapper<PersonItem>{
	
	public static String defaultDataSeparator = ",";
	
	private final String dataSeparator;//保证线程安全
	
	public PersonLineMapper() {
		this(defaultDataSeparator);
	}
	
	public PersonLineMapper(String dataSeparator) {
		this.dataSeparator = dataSeparator;
	}
	
	@Override
	public PersonItem mapLine(String line, int lineNumber) throws Exception {
		String[] str = line.split(dataSeparator, -1);
		PersonItem item = new PersonItem();
		item.setName(str[0]);
		item.setSex(str[1]);
		item.setAge(str[2]);
		return item;
	}
}