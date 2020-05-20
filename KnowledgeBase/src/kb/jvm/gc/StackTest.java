package kb.jvm.gc;

import java.util.concurrent.atomic.AtomicInteger;

public class StackTest {

	/**
	 * 
	 * -verbose:gc -Xms300M -Xmx300M -Xss1M -XX:+UseSerialGC -XX:+PrintGCDetails -XX:+PrintGCDateStamps
	 * 
	 * -Xss1M 30392
	 * -Xss2M 60392
	 * -Xss3M 90392
	 * -Xss4M 120392
	 * -Xss5M 150392
	 * 
	 * 结论：随着堆栈的增大，递归深度线性增大。
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		StackTest test = new StackTest();
		AtomicInteger num = new AtomicInteger(1);
		try {
			test.fun(num);
		} catch (Throwable t) {
			System.err.println(t.getCause());
		}
		System.out.println(num);
	}
	
	public void fun(AtomicInteger i){
		i.incrementAndGet();
		fun(i);
	}
}