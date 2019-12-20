package cn.edu.ahmu.cwc.test;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zk.basic.beans.factory.Summer;

public class VisitRequestTask implements Runnable{
	
	private static final Logger log = LoggerFactory.getLogger(VisitRequestTask.class);
	
	private final AtomicInteger globalSuccessCount;
	private final HttpClient httpClient;
	
	public VisitRequestTask(AtomicInteger globalSuccessCount, HttpClient httpClient) {
		this.globalSuccessCount = globalSuccessCount;
		this.httpClient = httpClient;
	}
	
	public VisitRequestTask(AtomicInteger globalSuccessCount) {
		this(globalSuccessCount, Summer.rain().getBean(HttpClient.class));
	}
	
	@Override
	public void run() {
        try {
        	CloseableHttpClient _httpClient = (CloseableHttpClient) httpClient;
    		//请求 - 关于安徽医科大学财务处中华人民共和国会计法的访问次数统计，这B返回关闭
            HttpGet httpGet = new HttpGet("http://cwc.ahmu.edu.cn/_visitcount?siteId=42&type=3&articleId=66495");
            CloseableHttpResponse response = _httpClient.execute(httpGet);
            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
            	int count = globalSuccessCount.incrementAndGet();
            	if(count % 100 == 0){
            		log.debug("Global Success request Count [{}]", count);
            	}
            }
        } catch (Exception e) {
            log.error("", e);
        }
	}
}