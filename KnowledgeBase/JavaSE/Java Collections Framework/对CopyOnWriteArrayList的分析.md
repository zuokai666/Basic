# 问题：着重分析CopyOnWriteArrayList的线程安全原理。
答案：
我想从函数角度来分析，所以以下先列出List接口的函数
	
	public interface List<E> {
	    boolean add(E e);//写操作
	    boolean remove(Object o);//写操作
	    E get(int index);//读操作
	    Iterator<E> iterator();//读操作
	}

第一个，boolean add(E e);//加锁

使用ReentrantLock加锁，保证线程安全，首先获取原数组，创建大小加一的新数组，原数据原封不动的复制过来，结尾设置新元素，最后重新设置array的引用。其中array的引用标记了volatile，保证线程间可见性。另外，函数不会更改旧数组元素，保证不变性。

第二个，boolean remove(Object o);//加锁

原理上同。同样的，函数不会更改旧数组元素，保证不变性。

第三个，E get(int index);//无锁

为什么不加锁可以保证线程安全呢？因为这里获取的是一个snapshot，由上述add(),remove()分析可得，函数不会更改旧数组元素。

第四个，Iterator<E> iterator();//无锁

为什么不加锁可以保证线程安全呢？因为这里获取的是一个不变的snapshot，后续的遍历是针对此不变的快照，自然没有线程问题。

## 这里引出了我的思考，同样都是保证线程安全，CopyOnWriteArrayList和Vector有什么根本区别呢？什么样的因造就效率差别的果呢？

以空间换效率

策略不同。同样都是数组，vector一直只使用一个数组，在之基础上进行add/remove，所以读操作必须加锁。而因为引入了快照数组的概念，所以COW会在写操作上带来一定的性能损失，好处就是读不加锁也可保证线程安全。因此，从COW的策略和设计初衷来看，只有在读多写少多线程竞争厉害的情况下，使用COW最佳，因为在此场景下完美切入COW的优点。相反，对于竞争不激烈，读写一半一半的情况，使用vector又有何不可呢，况且我们还能节省申请数组空间，节省复制数据的时间。终归来说，COW不是神，不能解决我们所有的问题，只有掌握它的特性，才能在合适的场景使用合适的集合。

PS:我突发奇想到了个坑。
对于我们常用的遍历操作，有两种写法：
	
	CopyOnWriteArrayList<Object> list = new CopyOnWriteArrayList<>();
	
	//第一种（ThreadNotSafe）:基于不同的数组引用，可能会出现异常。
	for(int i=0;i<list.size();i++){
		System.out.println(list.get(i));
	}
	
	//第二种（ThreadSafe）：基于同一不变的快照数组引用，不会出现异常。
	for(Object object : list){
		System.out.println(object);
	}
	
	//第三种（ThreadSafe）：和第二种一模一样，编译器会自动将第二种语法编译为iterator迭代器遍历的字节码，见上述iterator()函数的分析
	Iterator<Object> iterator = list.iterator();
	while(iterator.hasNext()){
		System.out.println(iterator.next());
	}

对于第一种情况的分析：我们平时所认为的线程安全是属于相对线程安全，不管是对于size(),还是get()来说都是线程安全的，但是组合到一起就有可能不安全，实属...坑...要注意，同样，vector也有同样的问题。

