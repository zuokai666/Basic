package com.zk.batch.model;

/**
 * 和文件对应的实体类
 * @author King
 *
 */
public class PersonItem {
	
	private String name;
	private String sex;
	private String age;
	
	public void setAge(String age) {
		this.age = age;
	}
	public String getAge() {
		return age;
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
	@Override
	public String toString() {
		return "PersonItem [name=" + name + ", sex=" + sex + ", age=" + age + "]";
	}
}