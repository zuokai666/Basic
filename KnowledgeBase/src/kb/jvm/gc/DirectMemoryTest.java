package kb.jvm.gc;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;


@SuppressWarnings("unused")
public class DirectMemoryTest {
	
	/**
	 * 
	 * -verbose:gc -Xms50M -Xmx50M -Xss1M -XX:MaxDirectMemorySize=50M -XX:+UseSerialGC -XX:+PrintGCDetails
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		final int _1M = 1024 * 1024;
//		List<ByteBuffer> buffers = new ArrayList<>();
//		int count = 1;
//		while (true) {
//			ByteBuffer byteBuffer = ByteBuffer.allocateDirect(_1M);
//			buffers.add(byteBuffer);
//			System.out.println(count++);
//		}
		
//		Field unsafeField = Unsafe.class.getDeclaredField("theUnsafe");
//	    unsafeField.setAccessible(true);
//	    Unsafe unsafe = (Unsafe) unsafeField.get(null);
//	    while (true) {
//	      unsafe.allocateMemory(_1M);
//	    }
	}
}