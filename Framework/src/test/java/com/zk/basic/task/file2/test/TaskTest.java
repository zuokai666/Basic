package com.zk.basic.task.file2.test;

import com.zk.basic.task.bootstrap.FileProcessStep;
import com.zk.basic.task.bootstrap.SimpleJobLauncher;
import com.zk.basic.task.bootstrap.SqlStep;
import com.zk.basic.task.config.DefaultFileReaderConfig;
import com.zk.basic.task.config.DefaultInsertDbConfig;
import com.zk.basic.task.config.FileReaderConfig;
import com.zk.basic.task.config.InsertDbConfig;
import com.zk.basic.task.consume.entity.support.InsertDbEntityHandler;
import com.zk.basic.test.Configuration;

public class TaskTest {
	
	public static void main(String[] args) {
		new Configuration().init();
		FileReaderConfig fileConfig = new DefaultFileReaderConfig(false, 0, "UTF-8", "D:\\test.txt", ",");
		InsertDbConfig insertDbConfig = new DefaultInsertDbConfig(5, 10, 
				"insert into Person(no,name,sex,age) values(?,?,?,?)", "Person");
		
		SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
		jobLauncher.addStep(new SqlStep("人员信息导入预执行sql", "truncate table Person"));
		jobLauncher.addStep(new FileProcessStep("人员信息导入", fileConfig, 
				insertDbConfig.getThreadCount(), () -> new InsertDbEntityHandler(insertDbConfig)));
		jobLauncher.run();
	}
}