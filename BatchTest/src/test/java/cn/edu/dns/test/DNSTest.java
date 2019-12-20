package cn.edu.dns.test;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 域名解析
 * @author King
 *
 */
public class DNSTest {
	
	public static void main(String[] args) throws UnknownHostException {
		InetAddress[] inetAddresses = InetAddress.getAllByName("www.d2p9.com");
		for(InetAddress inetAddress : inetAddresses){
			System.out.println(inetAddress);
		}
	}
}