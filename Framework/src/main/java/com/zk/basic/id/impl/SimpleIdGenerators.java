package com.zk.basic.id.impl;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zk.basic.id.IdGenerateConfig;
import com.zk.basic.id.IdGenerator;
import com.zk.basic.id.IdGenerators;

public class SimpleIdGenerators implements IdGenerators{
	
	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(SimpleIdGenerators.class);
	
	private ConcurrentMap<String, IdGenerator> map = new ConcurrentHashMap<>();
	private IdGenerateConfig idGenerateConfig;
	
	public SimpleIdGenerators(IdGenerateConfig idGenerateConfig) {
		this.idGenerateConfig = idGenerateConfig;
	}
	
	@Override
	public synchronized String generateId() {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public String generateId(String tableName) {
		return getIdGenerator(tableName).generateId();
	}
	
	@Override
	public IdGenerator getIdGenerator(String tableName) {
		IdGenerator idGenerator = map.get(tableName);
		if(idGenerator == null){
			IdGenerator newIdGenerator = new SimpleIdGenerator(idGenerateConfig);
			idGenerator = map.putIfAbsent(tableName, newIdGenerator);
			if(idGenerator == null){
				idGenerator = newIdGenerator;
			}
		}
		return idGenerator;
	}
}