package com.zk.basic.task.test;

import com.zk.basic.task.bootstrap.DataInDBStep;
import com.zk.basic.task.bootstrap.SimpleJobLauncher;
import com.zk.basic.task.config.DBConfig;
import com.zk.basic.task.config.DefaultDBConfig;
import com.zk.basic.task.config.DefaultFileConfig;
import com.zk.basic.task.config.FileConfig;
import com.zk.basic.task.process.DefaultFileProcessor;
import com.zk.basic.task.process.DefaultInsertDBProcessor;
import com.zk.basic.task.process.FileProcessor;
import com.zk.basic.task.process.InsertDBProcessor;

public class TaskTest {

	public static void main(String[] args) {
		FileConfig fileConfig = new DefaultFileConfig(false, 0, "UTF-8", "D:\test.txt", ",");
		FileProcessor fileProcessor = new DefaultFileProcessor(fileConfig);
		
		DBConfig dbConfig = new DefaultDBConfig(10, 10);
		InsertDBProcessor insertDBProcessor = new DefaultInsertDBProcessor(dbConfig);
		
		SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
		jobLauncher.addStep(new DataInDBStep("人员信息导入", fileProcessor, insertDBProcessor));
		jobLauncher.run();
	}
}