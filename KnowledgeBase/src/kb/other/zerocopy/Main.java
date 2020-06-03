package kb.other.zerocopy;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class Main {

	public static void main(String[] args) throws IOException {
		read_stream();
	}
	
	public static void read_stream() throws IOException{
		FileInputStream is = new FileInputStream(new File("D://test.txt"));
		byte[] buffer = new byte[1024];
		int length = 0;
		while((length = is.read(buffer)) != -1){
			System.out.println(new String(buffer, 0, length));
		}
		is.close();
	}
	
	// 分配堆内存
	public static void read_channel_heap() throws IOException{
		FileInputStream is = new FileInputStream(new File("D://test.txt"));
		FileChannel fc = is.getChannel();
		ByteBuffer bf = ByteBuffer.allocate(1024);
		int length = -1;
		while ((length = fc.read(bf)) != -1) {
			bf.clear();
			byte[] bytes = bf.array();
			System.out.println(new String(bytes, 0, length));
		}
		is.close();
	}
	
	// 分配堆外内存
	public static void read_channel() throws IOException{
		FileInputStream is = new FileInputStream(new File("D://test.txt"));
		FileChannel fc = is.getChannel();
		ByteBuffer bf = ByteBuffer.allocateDirect(1024);
		int length = -1;
		while ((length = fc.read(bf)) != -1) {
			bf.clear();
			byte[] bytes = bf.array();
			System.out.println(new String(bytes, 0, length));
		}
		is.close();
	}
}