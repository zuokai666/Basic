package com.zk.basic.beans.factory;

public interface SingletonBeanRegistry {
	
	<T> void registerSingleton(Class<T> requiredType, Object singletonObject);
	
	<T> T getBean(Class<T> requiredType);
	
	int getSingletonCount();
}