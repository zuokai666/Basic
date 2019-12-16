package com.zk.batch.model;

/**
 * 和文件对应的实体类
 * @author King
 *
 */
public class PersonItem {
	
	private String serial;
	private String name;
	private String sex;
	private String age;
	
	public void setAge(String age) {
		this.age = age;
	}
	public String getAge() {
		return age;
	}
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
}