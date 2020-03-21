package kb.jvm.metaspace;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class UserServiceInvocationHandler implements InvocationHandler{

	private UserService userService;
	
	public UserServiceInvocationHandler(UserService userService) {
		this.userService = userService;
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("begin");
		Object obj = method.invoke(userService);
        System.out.println("end");
        return obj;
	}

}
