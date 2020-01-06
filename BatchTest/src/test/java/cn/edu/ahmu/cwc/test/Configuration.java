package cn.edu.ahmu.cwc.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClients;

import com.zk.basic.beans.factory.Summer;

public class Configuration {
	
	public void init(){
		Summer.rain().registerSingleton(HttpClient.class, httpClient());
		Summer.rain().registerSingleton(ExecutorService.class, executorService());
	}
	
	public HttpClient httpClient(){
		RequestConfig config = RequestConfig.custom().setConnectTimeout(10_000).build();
		return HttpClients.custom()
				.setDefaultRequestConfig(config)
				.setMaxConnTotal(30)
				.setMaxConnPerRoute(30)
				.build();
	}
	
	public ExecutorService executorService(){
		ExecutorService threadPoolExecutor = new ThreadPoolExecutor(10, 100, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
//		threadPoolExecutor = new ThreadPool();
		return threadPoolExecutor;
	}
}