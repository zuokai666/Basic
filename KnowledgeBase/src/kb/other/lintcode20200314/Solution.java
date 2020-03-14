package kb.other.lintcode20200314;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Solution {
	
	//209. 第一个只出现一次的字符，多线程版本
	public static void main(String[] args) {
		new Solution().firstUniqChar("aabc");
	}
	
	public char firstUniqChar(String str) {
        if(str == null || str.length() == 0){
            return (char) -1;
        }
        int initialValue = str.length();
        AtomicInteger result = new AtomicInteger(initialValue);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(19,19,0,TimeUnit.SECONDS,new LinkedBlockingQueue<>());
        int blockSize = 10000;
        int start = 0;
        while(start <= str.length() - 1){
        	final int first = start;
        	final int end = start + blockSize - 1;
        	executor.execute(() -> firstUniqCharTask(str, first, end, result));
        	start = end;
        }
        executor.shutdown();
        try {
			executor.awaitTermination(10, TimeUnit.MINUTES);//最多等待10分钟
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        if(result.get() == initialValue){
        	return (char) -1;
        }else {
			return str.charAt(result.get());
		}
    }
	
	//利用string不可变原理，线程安全，全程只有查询操作，设置操作也已经加锁
	public void firstUniqCharTask(String str, int start, int end, AtomicInteger result){
		int actualEnd = end < str.length() ? end : str.length() - 1;
		LinkedHashMap<Character, Integer> map = new LinkedHashMap<>();
        for(int i=start;i<=actualEnd;i++){
        	char value = str.charAt(i);
        	map.put(value, map.getOrDefault(value, 0) + 1);
        }
        Iterator<Map.Entry<Character, Integer>> iterator = map.entrySet().iterator();
        while(iterator.hasNext()){
        	Map.Entry<Character, Integer> entry = iterator.next();
        	if(entry.getValue() == 1){
        		synchronized (result) {
					if(entry.getValue() < result.get()){
						result.set(entry.getValue());
					}
				}
				break;
        	}
        }
	}
}