package kb.javase.collections;

import java.util.HashMap;

public class HashMapTest {

	public static void main(String[] args) {
		HashMap<Integer, Integer> map = new HashMap<>();
		
		for(int i=0;i<200;i++){
			System.err.println(i);
			map.put(i * 16, i);
		}
	}
}