package com.zk.basic.test;

import javax.sql.DataSource;

import com.zk.basic.beans.factory.Summer;
import com.zk.basic.id.IdGenerateConfig;
import com.zk.basic.id.IdGenerators;
import com.zk.basic.id.impl.IdGenerateConfigRocket;
import com.zk.basic.id.impl.SimpleIdGenerators;
import com.zk.basic.lamborghini.LamborghiniConfig;
import com.zk.basic.lamborghini.LamborghiniDataSource;
import com.zk.basic.lamborghini.SimpleLamborghiniConfig;

public class Configuration {
	
	public void init(){
		Summer.rain().registerSingleton(LamborghiniConfig.class, lamborghiniConfig());
		Summer.rain().registerSingleton(DataSource.class, dataSource());
		Summer.rain().registerSingleton(IdGenerators.class, idGenerators());
	}
	
	public LamborghiniConfig lamborghiniConfig(){
		SimpleLamborghiniConfig lamborghiniConfig = new SimpleLamborghiniConfig();
		lamborghiniConfig.setUrl("jdbc:mysql://localhost:3306/test");
		lamborghiniConfig.setDriverClassName("com.mysql.jdbc.Driver");
		lamborghiniConfig.setUser("root");
		lamborghiniConfig.setPassword("d2p9bupt");
		lamborghiniConfig.setConnectionTimeout(30);
		lamborghiniConfig.setMaxPoolSize(10);
		lamborghiniConfig.setMinIdle(5);
		return lamborghiniConfig;
	}
	
	public DataSource dataSource(){
		LamborghiniDataSource dataSource = new LamborghiniDataSource(lamborghiniConfig());
		return dataSource;
	}
	
	public IdGenerators idGenerators(){
		IdGenerateConfigRocket rocket = new IdGenerateConfigRocket();
		IdGenerateConfig config = rocket.dataCenterId(IdGenerateConfig.dataCenterId)
									    .machineId(IdGenerateConfig.machineId)
									    .processId(IdGenerateConfig.processId)
									    .check()
									    .fire();
		IdGenerators idGenerators = new SimpleIdGenerators(config);
		return idGenerators;
	}
}