package kb.jvm.gc;

/**
 * G1 GC 测试，学习
 * 
 * @author King
 *
 */
public class G1Test {
	
	/**
	 * -Xms300M -Xmx300M -Xss1M -XX:+UseG1GC -XX:MaxGCPauseMillis=200 -XX:G1HeapRegionSize=1M 
	 * -XX:ParallelGCThreads=2
	 * -XX:+PrintGCDetails -XX:+PrintFlagsFinal
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		while(true){
			test();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public static void test(){
		@SuppressWarnings("unused")
		byte[] a = new byte[1024 * 1024 * 1];
	}
}