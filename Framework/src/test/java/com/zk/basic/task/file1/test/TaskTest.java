package com.zk.basic.task.file1.test;

import com.zk.basic.task.bootstrap.FileProcessStep;
import com.zk.basic.task.bootstrap.SimpleJobLauncher;
import com.zk.basic.task.bootstrap.SqlStep;
import com.zk.basic.task.config.ConnectionConfig;
import com.zk.basic.task.config.DefaultConnectionConfig;
import com.zk.basic.task.config.DefaultFileConfig;
import com.zk.basic.task.config.FileConfig;
import com.zk.basic.task.consume.entity.EntityHandler;
import com.zk.basic.task.consume.entity.support.PrintEntityHandler;

public class TaskTest {
	
	public static void main(String[] args) {
		FileConfig fileConfig = new DefaultFileConfig(false, 0, "UTF-8", "D:\\test.txt", ",");
		ConnectionConfig connectionConfig = new DefaultConnectionConfig(
				"jdbc:mysql://localhost:3306/test", "root", "d2p9bupt", "test");
		
		SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
		jobLauncher.addStep(new SqlStep("人员信息导入预执行sql", connectionConfig, "truncate table Person"));
		jobLauncher.addStep(new FileProcessStep("人员信息导入", fileConfig, 2, () -> createEntityHandler()));
		jobLauncher.run();
	}
	
	public static EntityHandler createEntityHandler(){
		return new PrintEntityHandler();
	}
}