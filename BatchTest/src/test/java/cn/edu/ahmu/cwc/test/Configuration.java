package cn.edu.ahmu.cwc.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;

import com.zk.basic.beans.factory.Summer;

public class Configuration {
	
	public void init(){
		Summer.rain().registerSingleton(HttpClient.class, httpClient());
		Summer.rain().registerSingleton(ExecutorService.class, executorService());
	}
	
	public HttpClient httpClient(){
//		return HttpClients.custom().setMaxConnTotal(10).build();
		return HttpClients.createDefault();
	}
	
	public ExecutorService executorService(){
		return new ThreadPoolExecutor(50, 50, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
	}
}