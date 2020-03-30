package kb.jvm.gc;

public class Test {

	public static void main(String[] args) {
		int a = 0x0ae00000;
		int b = 0x17600000;
		int c = b - a;// 104857600
		
		System.out.println(c / 1024 / 1024);
		
		int d = 1788 + 2242 + 2368 + 4480;
		System.err.println(d);
	}
}


/**


Heap
 def new generation   total 92160K, used 13517K [0x04a00000, 0x0ae00000, 0x0ae00000)
  eden space 81920K,  16% used [0x04a00000, 0x05733558, 0x09a00000) = 80 * 1024 * 1024 Byte 
  
  13841752 / 83886080 = 16.5%, 一共有80M, 使用了13.2M
  
  from space 10240K,   0% used [0x09a00000, 0x09a00000, 0x0a400000) = 10 * 1024 * 1024 Byte
  to   space 10240K,   0% used [0x0a400000, 0x0a400000, 0x0ae00000) = 10 * 1024 * 1024 Byte
 tenured generation   total 204800K, used 0K [0x0ae00000, 0x17600000, 0x17600000) = 200 * 1024 * 1024 Byte 
   the space 204800K,   0% used [0x0ae00000, 0x0ae00000, 0x0ae00200, 0x17600000)
 Metaspace       used 1788K, capacity 2242K, committed 2368K, reserved 4480K

Metaspace 总：= 10878 ？？？

*/