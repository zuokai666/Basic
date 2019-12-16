package com.zk.batch.test;

import java.util.Date;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;

import com.zk.basic.beans.factory.Summer;

public class BatchApp {
	
	public static void main(String[] args) {
		//注入模块
		new Configuration().init();
		//初始化任务参数
		JobParameters parameters = new JobParametersBuilder()
							.addDate("batch.date", new Date())
							.addString("user", "zuokai")
							.addLong("batch.seq.id", System.currentTimeMillis())
							.toJobParameters();
		//得到运行器
		JobLauncher launcher = Summer.rain().getBean(JobLauncher.class);
		Job job = Summer.rain().getBean(Job.class);
		try {
			launcher.run(job, parameters);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}