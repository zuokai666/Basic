package com.spring.batch.test;

import java.util.List;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.SimpleJob;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;

public class Test {
	
	public static void main(String[] args) throws Exception {
		JobRepository repository = new MapJobRepositoryFactoryBean().getObject();
		
		Step step = new StepBuilder("job")
				.repository(repository)
				.transactionManager(new ResourcelessTransactionManager())
				.<InputModel, OutputModel>chunk(10)
				.reader(new ItemReader<InputModel>() {
					private int i=20;
					@Override
					public InputModel read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
						if(i>0){
							System.err.println(Thread.currentThread().getName()+"read"+i);
							i--;
							return new InputModel();
						}else {
							return null;
						}
					}
				})
				.processor(new ItemProcessor<InputModel, OutputModel>() {
					
					@Override
					public OutputModel process(InputModel item) throws Exception {
						System.out.println(Thread.currentThread().getName()+item);
						return new OutputModel();
					}
				})
				.writer(new ItemWriter<OutputModel>() {
					
					@Override
					public void write(List<? extends OutputModel> items) throws Exception {
						System.err.println(Thread.currentThread().getName()+items.size());
					}
				})
				.build();
		SimpleJob simpleJob = new SimpleJob("job");
		simpleJob.setJobRepository(repository);
		simpleJob.addStep(step);
		simpleJob.execute(repository.createJobExecution("job", new JobParameters()));
	}
}