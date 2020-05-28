package kb.other.serial;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

public class HttpTest {

	public static void main(String[] args) {
		Socket client = null;
		try {
			client = new Socket();
			client.setSoTimeout(20);
			InetSocketAddress inetSocketAddress = new InetSocketAddress("121.42.143.23", 80);
			client.connect(inetSocketAddress, 10);
			PrintWriter pWriter = new PrintWriter(client.getOutputStream(), true);
			pWriter.println("GET / HTTP/1.1");
			pWriter.println("Host: localhost:8080");
			pWriter.println("Connection: keep-alive");
			pWriter.println("Cache-Control: max-age=0");
			pWriter.println("User-Agent: Socket");
			pWriter.println();// 注意这里要换行结束请求头
			
			String line = null;
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream(), "utf-8"));
			while((line = bufferedReader.readLine()) != null) {
			    System.out.println(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				client.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}