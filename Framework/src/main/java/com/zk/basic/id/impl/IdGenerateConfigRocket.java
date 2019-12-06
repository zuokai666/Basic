package com.zk.basic.id.impl;

import com.zk.basic.id.IdGenerateConfig;

/**
 * 使用建造者模式，不过我这次起名字为火箭，寓意第一步，第二步，等等，然后配置好零件后，检查，最后发射。
 * 
 * @author King
 *
 */
public class IdGenerateConfigRocket {
	
	private DefaultIdGenerateConfig config;
	
	public IdGenerateConfigRocket() {
		this.config = new DefaultIdGenerateConfig();
	}
	
	public IdGenerateConfigRocket dataCenterId(String dataCenterId){
		config.setDataCenterId(dataCenterId);
		return this;
	}
	
	public IdGenerateConfigRocket machineId(String machineId){
		config.setMachineId(machineId);
		return this;
	}
	
	public IdGenerateConfigRocket processId(String processId){
		config.setProcessId(processId);
		return this;
	}
	
	/**
	 * 检查
	 * @return
	 */
	public IdGenerateConfigRocket check(){
		if(config.getDataCenterId() == null){
			throw new IllegalArgumentException();
		}
		if(config.getMachineId() == null){
			throw new IllegalArgumentException();
		}
		if(config.getProcessId() == null){
			throw new IllegalArgumentException();
		}
		return this;
	}
	
	/**
	 * 发射
	 * @return
	 */
	public IdGenerateConfig fire(){
		return config;
	}
}