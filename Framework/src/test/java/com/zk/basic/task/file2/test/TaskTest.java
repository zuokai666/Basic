package com.zk.basic.task.file2.test;

import com.zk.basic.task.bootstrap.FileProcessStep;
import com.zk.basic.task.bootstrap.SimpleJobLauncher;
import com.zk.basic.task.config.DefaultFileConfig;
import com.zk.basic.task.config.FileConfig;
import com.zk.basic.task.consume.entity.EntityHandler;
import com.zk.basic.task.consume.entity.support.PrintEntityHandler;
import com.zk.basic.task.process.DefaultFileItemReader;
import com.zk.basic.task.process.FileItemReader;

public class TaskTest {
	
	public static void main(String[] args) {
		FileConfig fileConfig = new DefaultFileConfig(false, 0, "UTF-8", "D:\\test.txt", ",");
		FileItemReader fileItemReader = new DefaultFileItemReader(fileConfig);
		
//		SqlHolder sqlHolder = new DefaultSqlHolder("insert into Person(no,name,sex,age)values(?,?,?,?)");
//		sqlHolder.addPreSql("truncate table Person");
//		DBConfig dbConfig = new DefaultDBConfig(50, 500, "jdbc:mysql://localhost:3306/test", "root", "d2p9bupt", sqlHolder);
//		InsertDBProcessor insertDBProcessor = new DefaultInsertDBProcessor(dbConfig);
//		
		SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
		jobLauncher.addStep(new FileProcessStep("人员信息导入", fileItemReader, 3, () -> createEntityHandler()));
		jobLauncher.run();
	}
	
	public static EntityHandler createEntityHandler(){
		return new PrintEntityHandler();
	}
}