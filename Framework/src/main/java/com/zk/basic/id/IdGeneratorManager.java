package com.zk.basic.id;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.zk.basic.id.impl.SimpleIdGenerator;

/**
 * 不同的表用不同的主键生成策略
 * 
 * @author King
 *
 */
public class IdGeneratorManager {
	
	private static ConcurrentMap<String, IdGenerator> map = new ConcurrentHashMap<>();
	
	public static IdGenerator getByTable(IdGenerateConfig idGenerateConfig){
		IdGenerator idGenerator = map.get(idGenerateConfig.getTableName());
		if(idGenerator == null){
			IdGenerator newIdGenerator = new SimpleIdGenerator(idGenerateConfig);
			idGenerator = map.putIfAbsent(idGenerateConfig.getTableName(), newIdGenerator);
			if(idGenerator == null){
				idGenerator = newIdGenerator;
			}
		}
		return idGenerator;
	}
}