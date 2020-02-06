package kb.javase.collections;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class CollectionTest {
	
	/**
	 * list.add true
	 * 
	 * set.add false
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		List<Integer> list = new ArrayList<>();
		list.add(1);
		boolean result1 = list.add(1);
		System.out.println("list.add " + result1);
		
		Set<Integer> set = new TreeSet<>();
		set.add(1);
		boolean result2 = set.add(1);
		System.out.println("set.add " + result2);
	}
}