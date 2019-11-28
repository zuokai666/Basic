package com.zk.basic.task.item.support;

import java.util.List;

import com.zk.basic.task.item.ItemReader;

public interface DbKeyItemReader extends ItemReader<List<String>>{
	
	String getPrimaryKeyName();
}