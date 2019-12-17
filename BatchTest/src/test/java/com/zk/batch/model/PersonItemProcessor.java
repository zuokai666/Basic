package com.zk.batch.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import com.zk.basic.beans.factory.Summer;
import com.zk.basic.id.IdGenerators;

public class PersonItemProcessor implements ItemProcessor<PersonItem, PersonEntity>{
	
	private static final Logger log = LoggerFactory.getLogger(PersonItemProcessor.class);
	
	@Override
	public PersonEntity process(PersonItem item) throws Exception {
		log.debug("begin process [{}]", item);
		try {
			String serial = Summer.rain().getBean(IdGenerators.class).generateId("person");
			PersonEntity entity = new PersonEntity();
			entity.setSerial(serial);
			entity.setName(item.getName());
			entity.setSex(item.getSex());
			entity.setAge(Integer.parseInt(item.getAge()));
			return entity;
		} catch (Exception e) {
			log.error("item process error : " + item.toString(), e);
		}
		return null;
	}
}