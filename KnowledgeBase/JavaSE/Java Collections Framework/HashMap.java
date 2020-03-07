
/**
 * JDK 1.8
 */
public class HashMap {
	
	private final float loadFactor = 0.75f;
	
	public HashMap() {
		//懒加载，当创建对象时，不会创建内部数组
	}
	
	private final int hash(Object key){
		if(key == null){
			return 0;
		}
		int h = key.hashCode();
		return h ^ (h >>> 16);//高低异或，如果高16位都是0，则不变
	}
}