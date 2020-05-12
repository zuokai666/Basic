package kb.jvm.str;

import java.util.Date;

public class Main {
	
	
	/**
	 * 
	 * Epoch，时期； 纪元；世；新时代；指的是一个特定的时间：1970-01-01 00:00:00 UTC
	 * 
	 * 通常我们说的时间戳，就是指格林威治时间（GMT）1970年01月01日00时00分00秒起至现在的总秒数。
	 * 
	 * 纪元时间与时区
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Date date = new Date(0);// Thu Jan 01 08:00:00 CST 1970
		
		date = new Date(1000);// Thu Jan 01 08:00:01 CST 1970
		
		System.out.println(date);
	}
}