package cn.edu.dns.test;

import java.net.InetAddress;

import com.googlecode.ipv6.IPv6Address;

public class IpTest {
	
	public static void main(String[] args) throws Exception {
		System.setProperty("java.net.preferIPv6Addresses", "true");
		InetAddress inetAddress = InetAddress.getLocalHost();
		System.out.println(inetAddress.getHostAddress());
		
		IPv6Address iPv6Address = IPv6Address.fromString("fe80:0:0:0:34c1:6c93:834a:4ae9");
		System.err.println(iPv6Address);
	}
}