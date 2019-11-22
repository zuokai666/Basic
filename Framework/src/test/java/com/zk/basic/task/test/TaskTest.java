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
import com.zk.basic.task.sql.DefaultSqlHolder;
import com.zk.basic.task.sql.SqlHolder;

public class TaskTest {
	
	public static void main(String[] args) {
		FileConfig fileConfig = new DefaultFileConfig(false, 0, "UTF-8", "D:\\test.txt", ",");
		FileProcessor fileProcessor = new DefaultFileProcessor(fileConfig);
		
		SqlHolder sqlHolder = new DefaultSqlHolder("insert into Person(no,name,sex,age)values(?,?,?,?)");
		sqlHolder.addPreSql("truncate table Person");
		DBConfig dbConfig = new DefaultDBConfig(50, 500, "jdbc:mysql://localhost:3306/test", "root", "d2p9bupt", sqlHolder);
		InsertDBProcessor insertDBProcessor = new DefaultInsertDBProcessor(dbConfig);
		
		SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
		jobLauncher.addStep(new DataInDBStep("人员信息导入", fileProcessor, insertDBProcessor));
		jobLauncher.run();
	}
}