类加载机制

由java.lang.ClassLoader.loadClass(类全限定名)返回Class<?>对象

根据类全限定名创建了锁，防止不同线程加载了同一个类，然后检查该类是否已经被加载，找到它
synchronized (getClassLoadingLock(name)) {
    Class<?> c = findLoadedClass(name);
    if(c == null){
    	c = parent.loadClass();
    }
    if(c == null){
    	c = findClass(name);
    }
}

双亲委派模型：单向，向上委托的模型
Bootstrap ClassLoader <- Extension ClassLoader <- Application ClassLoader

一个破坏双亲委派模型的例子：
java.sql.DriverManager需要加载厂商的java.sql.Driver实现子类。
Bootstrap ClassLoader肯定是够不到子类了，因为它委托Application ClassLoader来加载实现子类。



类加载过程分为：加载，验证，准备，解析，初始化，使用，卸载

对于初始化阶段，有且只有5种情况必须初始化：
	
	1.遇到new,getstatic,putstatic,invokestatic这4个字节码指令时，开始初始化
	2.使用反射创建对象时
	3.先初始化父类，后初始化子类
	4.启动main方法的类会初始化
	
