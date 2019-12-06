package com.zk.basic.beans.factory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.zk.basic.beans.exception.NoSuchBeanException;
import com.zk.basic.beans.exception.UnsupportedTypeException;

/**
 * 一个工厂，隔绝对其它组件的依赖，只依赖接口，不依赖实现。
 * 
 * @author King
 * 
 */
public class Summer implements SingletonBeanRegistry{
	
	//饿汉式单例模式
	private static final Summer instance = new Summer();
	private Summer() {}
	public static SingletonBeanRegistry rain(){
		return instance;
	}
	
	//容器
	private ConcurrentMap<Class<?>, Object> map = new ConcurrentHashMap<>();
	
	@Override
	public <T> void registerSingleton(Class<T> requiredType, Object singletonObject) {
		if(requiredType.isInterface()){
			map.put(requiredType, singletonObject);
		}else {
			throw new UnsupportedTypeException(requiredType.getName());
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getBean(Class<T> requiredType) {
		Object object = map.get(requiredType);
		if(object == null){
			throw new NoSuchBeanException(requiredType.getName());
		}
		return (T)object;
	}
	
	@Override
	public int getSingletonCount() {
		return map.size();
	}
}