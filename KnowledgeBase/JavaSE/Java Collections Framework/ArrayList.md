基于可变大小的数组的List接口的实现。

允许添加的元素重复，也允许null元素。

线程安全：
	
	List list = Collections.synchronizedList(new ArrayList(...));
	
	List list = new CopyOnWriteArrayList<>();//效率更高，速度更快
	
	
涉及到数组列表的类有：Vector，ArrayList，CopyOnWriteArrayList


