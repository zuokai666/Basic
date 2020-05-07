package kb.other.random;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Test {

	// Shuffle array
	public static void main(String[] args) {
		List<Integer> list = Arrays.asList(3,4,1,2);
		for(int i=0;i<1;i++){
			Collections.shuffle(list);
			System.err.println(list);
		}
	}
}