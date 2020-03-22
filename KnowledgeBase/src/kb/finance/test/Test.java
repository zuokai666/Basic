package kb.finance.test;

import java.math.BigDecimal;

public class Test {

	public static void main(String[] args) {
		System.out.println(0.05 + 0.01);// 0.060000000000000005
		System.out.println(1.0 - 0.42);// 0.5800000000000001
		System.out.println(4.015 * 100.0);// 401.49999999999994
		System.out.println(123.3 / 100.0);// 1.2329999999999999
		
		BigDecimal a = new BigDecimal(1);
		System.out.println(a);
	}
}