package kb.jvm.classload;

public class CLTest {
	
	public static void main(String[] args) {
		//先初始化父类，后初始化子类
		@SuppressWarnings("unused")
		Son son = new Son();
		
//		try {
//			Class<?> clasz = Class.forName("kb.jvm.classload.Son");
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		}
		
		//不会触发子类初始化 - (被动引用)
		//System.err.println(Son.age);
		//Son.test();
		
		//通过数组定义引用类，不会触发 - (被动引用)
		//Son[] sons = new Son[10];
		
		//引用常量，不会触发 - (被动引用)
		//System.err.println(Son.length);
	}
}