package kb.jvm.classload;

public class Father {
	
	static{
		System.out.println("Father static");
	}
	
	protected static int age = 10;
	
	protected final static int length = 100;
	
	protected static void test(){
		
	}
}