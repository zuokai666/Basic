package kb.other.zerocopy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

public class Test {

	public static void main(String[] args) throws IOException {
		
		fun2();
	}

	private static void fun2() throws FileNotFoundException, IOException {
		FileOutputStream fos = new FileOutputStream(new File("D://test.copy.txt"), true);
		RandomAccessFile raf = new RandomAccessFile(new File("D://test.txt"), "rw");
		FileChannel fileChannel = raf.getChannel();
		// 直接使用了transferTo()进行通道间的数据传输
		fileChannel.transferTo(0, fileChannel.size(), fos.getChannel());
		fos.close();
		raf.close();
	}
}