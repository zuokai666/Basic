package cn.edu.ahmu.cwc.test;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zk.basic.beans.factory.Summer;

public class VisitD2P9Task implements Runnable{
	
	private static final Logger log = LoggerFactory.getLogger(VisitD2P9Task.class);
	
	private final AtomicInteger globalSuccessCount;
	private final HttpClient httpClient;
	
	public VisitD2P9Task(AtomicInteger globalSuccessCount, HttpClient httpClient) {
		this.globalSuccessCount = globalSuccessCount;
		this.httpClient = httpClient;
	}
	
	public VisitD2P9Task(AtomicInteger globalSuccessCount) {
		this(globalSuccessCount, Summer.rain().getBean(HttpClient.class));
	}
	
	@Override
	public void run() {
        try {
            HttpGet httpGet = new HttpGet("http://www.d2p9.com/");
            HttpResponse response = httpClient.execute(httpGet);
            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
            	int count = globalSuccessCount.incrementAndGet();
            	if(count % 100 == 0){
            		log.debug("Global Success request Count [{}]", count);
            	}
            }
            EntityUtils.consume(response.getEntity());//归还连接操作
        } catch (Exception e) {
            log.error("", e);
        }
	}
}