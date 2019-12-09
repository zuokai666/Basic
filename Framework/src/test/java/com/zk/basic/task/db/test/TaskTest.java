package com.zk.basic.task.db.test;

import java.sql.ResultSet;

import com.zk.basic.task.bootstrap.DbProcessStep;
import com.zk.basic.task.bootstrap.SimpleJobLauncher;
import com.zk.basic.task.config.DefaultFileWriterConfig;
import com.zk.basic.task.config.DefaultQueryDbConfig;
import com.zk.basic.task.config.FileWriterConfig;
import com.zk.basic.task.config.QueryDbConfig;
import com.zk.basic.task.consume.entity.EntityHandler;
import com.zk.basic.task.consume.entity.support.QueryDbEntityHandler;
import com.zk.basic.test.Configuration;

public class TaskTest {
	
	public static void main(String[] args) {
		new Configuration().init();
		FileWriterConfig fileConfig = new DefaultFileWriterConfig("UTF-8", "D:\\test.txt", ",");
		QueryDbConfig queryDbConfig = new DefaultQueryDbConfig(15, "select no from person", "no");
		
		SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
		jobLauncher.addStep(new DbProcessStep("人员信息导出", queryDbConfig, fileConfig, 
				() -> createEntityHandler(queryDbConfig, fileConfig)));
		jobLauncher.run();
	}
	
	public static EntityHandler createEntityHandler(QueryDbConfig queryDbConfig, FileWriterConfig fileConfig){
		return new QueryDbEntityHandler(queryDbConfig, fileConfig) {
			
			@Override
			protected String doQuery(String key, String fileSperator) throws Exception {
				ResultSet resultSet = statement.executeQuery("select * from person where no = '"+key+"'");
				StringBuilder sb = new StringBuilder();
				while(resultSet.next()){
					sb.append(resultSet.getString("name"));
					sb.append(fileSperator);
					sb.append(resultSet.getString("sex"));
					sb.append(fileSperator);
					sb.append(resultSet.getString("age"));
					sb.append(System.getProperty("line.separator"));
				}
				return sb.toString();
			}
		};
	}
}