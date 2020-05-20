package kb.other.random;

import java.util.TreeSet;

public class PossibleReordering {

	static int x = 0;
	static int y = 0;
	static int a = 0;
	static int b = 0;
	
	static TreeSet<String> set = new TreeSet<>();
	
	public static void main(String[] args) {
		for(int i=0;i<100000;i++){
			x = 0;
			y = 0;
			a = 0;
			b = 0;
			
			Thread one = new Thread(() -> {a = 1; x = b;});
			Thread two = new Thread(() -> {b = 1; y = a;});
			
			one.start();
			two.start();
			
			try {
				one.join();
				two.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			set.add(x + "" + y);
		}
		System.err.println(set);
	}
}