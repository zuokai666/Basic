package com.zk.batch.model;

/**
 * 和数据库对应的实体类
 * @author King
 * 
 */
public class PersonEntity {
	
	private String serial;
	private String name;
	private String sex;
	private int age;
	
	public String getSerial() {
		return serial;
	}
	public void setSerial(String serial) {
		this.serial = serial;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}	
}