package kb.jvm.gc;

/**
 * Serial + Serial Old GC 测试，学习
 * 
 * @author King
 * 
 */
public class SerialTest {
	
	/**
	 * -verbose:gc -Xms300M -Xmx300M -Xss1M -XX:+UseSerialGC -XX:+PrintGCDetails -XX:+PrintGCDateStamps
	 * 
	 * -XX:InitialHeapSize=314572800 -XX:MaxHeapSize=314572799 -Xss1M -XX:+UseSerialGC -XX:+PrintGCDetails
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		for(int i=0;i<200000000;i++){
			test();
			if(i == 9 || i == 19){
				Runtime.getRuntime().gc();
			}
		}
	}
	
	public static void test(){
		@SuppressWarnings("unused")
		byte[] a = new byte[1024 * 1024 * 1];
	}
}