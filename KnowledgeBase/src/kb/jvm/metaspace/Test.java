package kb.jvm.metaspace;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

public class Test {
	/**
	 * -Xms10m -Xmx10m -XX:MetaspaceSize=10m -XX:MaxMetaspaceSize=10m -XX:+PrintGCDetails
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		UserService target = new UserServiceImpl();
		UserServiceInvocationHandler invocationHandler = new UserServiceInvocationHandler(target);
		List<UserService> list = new ArrayList<UserService>();
		while(true){
			UserService userService = (UserService) Proxy.newProxyInstance(
					target.getClass().getClassLoader(), 
					target.getClass().getInterfaces(), 
					invocationHandler);
			System.err.println(userService.getClass());//class com.sun.proxy.$Proxy0
			userService.login();
			list.add(userService);
		}
	}
}