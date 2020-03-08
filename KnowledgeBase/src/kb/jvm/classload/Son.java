package kb.jvm.classload;

public class Son extends Father{

	static{
		System.out.println("Son static");
	}
	
	{
		System.out.println(1);
	}
	
	public Son() {
		System.out.println(3);//最后初始化
	}
	
	{
		System.out.println(2);
	}
	
	public int high = 23;
}
