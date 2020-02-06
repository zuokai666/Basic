package kb.javase.collections;

import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayListTest {

	public static void main(String[] args) {
		CopyOnWriteArrayList<CopyOnWriteArrayListTest> list = new CopyOnWriteArrayList<>();
		list.add(new CopyOnWriteArrayListTest());
		System.out.println(list);
		
		list.add(new CopyOnWriteArrayListTest());
		System.out.println(list);
	}
}