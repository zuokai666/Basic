package com.zk.basic.id.impl;

import com.zk.basic.id.IdGenerateConfig;

public class DefaultIdGenerateConfig implements IdGenerateConfig{
	
	private String dataCenterId;
	private String machineId;
	private String processId;
	
	@Override
	public String getDataCenterId() {
		return dataCenterId;
	}
	public void setDataCenterId(String dataCenterId) {
		this.dataCenterId = dataCenterId;
	}
	@Override
	public String getMachineId() {
		return machineId;
	}
	public void setMachineId(String machineId) {
		this.machineId = machineId;
	}
	@Override
	public String getProcessId() {
		return processId;
	}
	public void setProcessId(String processId) {
		this.processId = processId;
	}
}