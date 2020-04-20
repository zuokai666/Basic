package kb.jvm.str;

public class Test {
	
	public static void main(String[] args) {
		String a = "abc";
		String b = a + "";
		System.err.println(a == b);
	}
}