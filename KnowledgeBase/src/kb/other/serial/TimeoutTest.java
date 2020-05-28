package kb.other.serial;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class TimeoutTest {
	
	public static void main(String[] args) throws IOException {
		connectionTimeout();
//		futureTimeout();
		
		
	}
	
	public static void futureTimeout() {
		FutureTask<Boolean> futureTask = new FutureTask<>(new SleepTask(3000));
		new Thread(futureTask).start();
		try {
			Boolean result = futureTask.get(1000, TimeUnit.MILLISECONDS);
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void connectionTimeout() throws MalformedURLException, IOException {
		URL url = new URL("http://www.d2p9.com/");
		URLConnection conn = url.openConnection();
		conn.setConnectTimeout(100);
		conn.setReadTimeout(10);
		System.out.println("连接超时：" + conn.getConnectTimeout());
		System.out.println("读取超时：" + conn.getReadTimeout());
		conn.connect();
		InputStream inStream = conn.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(inStream));
		String line = null;
		while((line = br.readLine()) != null){
			System.out.println(line);
		}
		br.close();
	}
}