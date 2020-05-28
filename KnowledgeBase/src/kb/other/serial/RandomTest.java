package kb.other.serial;

import java.util.Arrays;
import java.util.Random;

public class RandomTest {
	
	public static void main(String[] args) {
		Random random = new Random();
		int[] bucket = new int[10];
		int time = 100_0000;
		for(int i=0;i<time;i++){
			int value = random.nextInt(bucket.length);
			bucket[value]++;
		}
		System.out.println(Arrays.toString(bucket));
	}
}

/**

int time = 1_0000;
[1019, 1018, 1017, 991, 936, 1021, 953, 1018, 1008, 1019]

int time = 10_0000;
[10083, 10047, 10096, 9980, 9855, 9968, 10070, 9968, 9960, 9973]

int time = 100_0000;
[99866, 100196, 100338, 99939, 100093, 99933, 99351, 100278, 100235, 99771]

*/