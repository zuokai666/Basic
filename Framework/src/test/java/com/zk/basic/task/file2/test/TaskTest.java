package com.zk.basic.task.file2.test;

import com.zk.basic.task.bootstrap.FileProcessStep;
import com.zk.basic.task.bootstrap.SimpleJobLauncher;
import com.zk.basic.task.bootstrap.SqlStep;
import com.zk.basic.task.config.DefaultFileReaderConfig;
import com.zk.basic.task.config.DefaultInsertDbConfig;
import com.zk.basic.task.config.FileReaderConfig;
import com.zk.basic.task.config.InsertDbConfig;
import com.zk.basic.task.consume.entity.EntityHandler;
import com.zk.basic.task.consume.entity.support.InsertDbEntityHandler;

public class TaskTest {
	
	public static void main(String[] args) {
		FileReaderConfig fileConfig = new DefaultFileReaderConfig(false, 0, "UTF-8", "D:\\test.txt", ",");
		InsertDbConfig insertDbConfig = new DefaultInsertDbConfig(20, 500, 
				"jdbc:mysql://localhost:3306/test", "root", "d2p9bupt", 
				"insert into Person(no,name,sex,age) values(?,?,?,?)", "test", "Person");
		
		SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
		jobLauncher.addStep(new SqlStep("人员信息导入预执行sql", insertDbConfig, "truncate table Person"));
		jobLauncher.addStep(new FileProcessStep("人员信息导入", fileConfig, 
				insertDbConfig.getThreadCount(), 
				() -> createEntityHandler(insertDbConfig)));
		jobLauncher.run();
	}
	
	public static EntityHandler createEntityHandler(InsertDbConfig insertDbConfig){
		return new InsertDbEntityHandler(insertDbConfig);
	}
}