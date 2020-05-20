package kb.jvm.gc;

public class ThreadLocalTest {
	
	/**
	 * -verbose:gc -Xms300M -Xmx300M -Xss1M -XX:+UseSerialGC -XX:+PrintGCDetails -XX:+PrintGCDateStamps
	 * @param args
	 */
	public static void main(String[] args) {
		ThreadLocal<String> threadLocal = new ThreadLocal<>();
		if(threadLocal.get() == null){
			threadLocal.set("zuokai");
		}
		for(int i=0;i<100;i++){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(i == 5){
				threadLocal = null;
			}
			if(i == 10){
				Thread thread = Thread.currentThread();
				System.err.println(thread.toString());
			}
			if(threadLocal != null){
				System.out.println(threadLocal.get());
			}
			System.gc();
		}
	}
}