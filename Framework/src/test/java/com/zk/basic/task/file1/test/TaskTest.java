package com.zk.basic.task.file1.test;

import com.zk.basic.task.bootstrap.FileProcessStep;
import com.zk.basic.task.bootstrap.SimpleJobLauncher;
import com.zk.basic.task.config.DefaultFileConfig;
import com.zk.basic.task.config.FileConfig;
import com.zk.basic.task.consume.entity.EntityHandler;
import com.zk.basic.task.consume.entity.support.PrintEntityHandler;

public class TaskTest {
	
	public static void main(String[] args) {
		FileConfig fileConfig = new DefaultFileConfig(false, 0, "UTF-8", "D:\\test.txt", ",");
		
		SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
		jobLauncher.addStep(new FileProcessStep("人员信息导入1", fileConfig, 2, () -> createEntityHandler()));
		jobLauncher.addStep(new FileProcessStep("人员信息导入2", fileConfig, 2, () -> createEntityHandler()));
		jobLauncher.run();
	}
	
	public static EntityHandler createEntityHandler(){
		return new PrintEntityHandler();
	}
}