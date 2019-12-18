package cn.edu.ahmu.cwc.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zk.basic.beans.factory.Summer;
import com.zk.basic.util.StopWatch;

public class VisitRocket {
	
	private static final Logger log = LoggerFactory.getLogger(VisitRocket.class);
	
	public static void main(String[] args) {
        new Configuration().init();
        ExecutorService executorService = Summer.rain().getBean(ExecutorService.class);
        AtomicInteger globalSuccessCount = new AtomicInteger(0);
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("请求任务");
        for(int i=0;i<1;i++){
//        	executorService.execute(new VisitRequestTask(globalSuccessCount));
        	executorService.execute(new VisitD2P9Task(globalSuccessCount));
        }
        executorService.shutdown();
        try {
			executorService.awaitTermination(100000, TimeUnit.DAYS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        stopWatch.normalStop();
        log.info("Global Success request Count [{}]", globalSuccessCount.get());
//      LockSupport.park();
	}
}