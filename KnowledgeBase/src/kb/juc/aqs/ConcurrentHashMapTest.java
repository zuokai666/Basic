package kb.juc.aqs;

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapTest {
	
	public static void main(String[] args) {
		ConcurrentHashMap<Integer, Integer> chm = new ConcurrentHashMap<>(64);
		for(int i=0;i<100;i++){
			System.err.println(i);
			chm.put(i * 64 + 0, i * 64 + 0);
		}
	}
}