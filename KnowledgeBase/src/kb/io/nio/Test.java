package kb.io.nio;

import java.io.IOException;
import java.nio.channels.Selector;
import java.util.concurrent.locks.LockSupport;

public class Test {
	
	public static void main(String[] args) {
		try {
			for(int i=0;i<10000000;i++){
				@SuppressWarnings("unused")
				Selector selector = Selector.open();
				

			}
			LockSupport.park();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}