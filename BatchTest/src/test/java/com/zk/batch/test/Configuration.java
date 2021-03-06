package com.zk.batch.test;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.SyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

import com.zk.basic.beans.factory.Summer;
import com.zk.basic.id.IdGenerateConfig;
import com.zk.basic.id.IdGenerators;
import com.zk.basic.id.impl.IdGenerateConfigRocket;
import com.zk.basic.id.impl.SimpleIdGenerators;
import com.zk.basic.lamborghini.LamborghiniConfig;
import com.zk.basic.lamborghini.LamborghiniDataSource;
import com.zk.basic.lamborghini.SimpleLamborghiniConfig;
import com.zk.basic.lamborghini.listener.support.ConnectionResetListener;
import com.zk.batch.model.PersonEntity;
import com.zk.batch.model.PersonItem;
import com.zk.batch.model.PersonItemProcessor;
import com.zk.batch.model.PersonItemWriter;
import com.zk.batch.model.PersonLineMapper;

public class Configuration {
	
	public static int chunkSize = 10;
	public static String dataSeparator = ",";
	
	public void init(){
		Summer.rain().registerSingleton(LamborghiniConfig.class, lamborghiniConfig());
		Summer.rain().registerSingleton(DataSource.class, dataSource());
		Summer.rain().registerSingleton(IdGenerators.class, idGenerators());
		
		Summer.rain().registerSingleton(JobRepository.class, jobRepository());//先初始化JobRepository，后续有调用
		
		Summer.rain().registerSingleton(JobLauncher.class, jobLauncher());
		Summer.rain().registerSingleton(Job.class, job());
	}
	
	public JobLauncher jobLauncher(){
		JobRepository jobRepository = Summer.rain().getBean(JobRepository.class);
		SimpleJobLauncher simpleJobLauncher = new SimpleJobLauncher();
		simpleJobLauncher.setJobRepository(jobRepository);
		simpleJobLauncher.setTaskExecutor(new SyncTaskExecutor());
		return simpleJobLauncher;
	}
	
	public Job job(){
		JobRepository jobRepository = Summer.rain().getBean(JobRepository.class);
		
		Job job = new JobBuilder("job - 1")
				.repository(jobRepository)
				.start(step1())
				.build();
		return job;
	}
	
	public Step step1(){
		JobRepository jobRepository = Summer.rain().getBean(JobRepository.class);
		
		FlatFileItemReader<PersonItem> reader = new FlatFileItemReader<PersonItem>();
		reader.setResource(new DefaultResourceLoader().getResource("file:D:\\test.txt"));
		reader.setLineMapper(new PersonLineMapper());
		PersonItemProcessor processor = new PersonItemProcessor();
		PersonItemWriter writer = new PersonItemWriter();
		
		TaskExecutor taskExecutor = new SimpleAsyncTaskExecutor();
		Step step = new StepBuilder("step - 1")
				.repository(jobRepository)
				.transactionManager(new ResourcelessTransactionManager())
				.<PersonItem, PersonEntity>chunk(chunkSize)
				.reader(reader)
				.processor(processor)
				.writer(writer)
				.taskExecutor(taskExecutor )
				.build();
		return step;
	}
	
	public JobRepository jobRepository() {
		JobRepository repository = null;
		try {
			repository = new MapJobRepositoryFactoryBean().getObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return repository;
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
		dataSource.addListener(new ConnectionResetListener());//加入重置属性监听器，满足开闭原则
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