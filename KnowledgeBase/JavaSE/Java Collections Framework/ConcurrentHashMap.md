# 问题：着重分析ConcurrentHashMap的线程安全原理。
答案：首先ConcurrentHashMap作为一个哈希表，采用数组+单链表+红黑树的数据结构，使用链地址法来解决哈希冲突问题，当单链表达到阈值时，将链表转化为红黑树，时间复杂度从O(k)将缩减至O(logk)。还有一点需要注意，就是构造函数中不会初始化数组，也就是采用的是懒汉式策略，只会在放入元素时才会初始化数组。接下来从Map接口的函数角度来分析ConcurrentHashMap的线程安全原理，另外，因为着重分析并发问题，所以不会涉及hash计算之类的东西。

	public interface Map<K,V> {
		V put(K key, V value);
	    V get(Object key);//不加锁
	}

第一个，put函数：

1.上面提到的数组未初始化，所以在初始化数组这里将会是多线程竞争的第一站。有两种实现方法(伪代码)：

第一种，单例模式中的双重判断检查锁，阻塞同步方式，悲观锁：（自己的思路，效率应该不高）

	private final Node<K,V>[] initTable() {
		if((table == null || table.length == 0) {
			synchronized(this){
				if((tab = table) == null || tab.length == 0) {
					table = new Node[DEFAULT_CAPACITY];
				}
			}
		}
		return table;
	}

第二种，死循环+cas操作，非阻塞同步方式，乐观锁，还有自旋的味道。（ConcurrentHashMap使用的是这种方式）

	private final Node<K,V>[] initTable() {
	    while (table == null || table.length == 0) {
	        if (U.compareAndSet(this, SIZECTL) == success) {//只有设置成功的线程才能初始化数组，其他的自旋吧。
	            if ((tab = table) == null || tab.length == 0) {
	            	table = new Node[DEFAULT_CAPACITY];
	            }
	            break;
	        }
	    }
	    return table;
	}

接下来，所有的线程都已经退出了该函数，都获得同一份数组引用。

2.用key计算位置，使用cas操作来设置数组中的元素（这里指的是单链表的第一个元素），ConcurrentHashMap的机制和上述初始化数组一样，死循环+cas操作来保证线程安全，保证只有一个线程才能设置单链表的头元素。一个幸运儿线程设置成功之后，其它线程只能悻悻而归，转到其它的else判断条件中了。

3.接下来描述多线程竞争插入同一位置的元素，使用synchronized(头元素)来锁住单链表或者说红黑树，接下来就不用分析了，因为同步块中只有一个线程访问了，不涉及并发了，辣个幸运儿线程抢到了锁，进行自己的put就好了。

综上所述，当多线程插入不同的位置的元素时，它们之间的竞争很少了，因为synchronized锁的粒度是后面的单链表/红黑树，通过减小锁的粒度来提高并发性，这一点是相对于Hashtable来说，将整个插入统一锁住，ConcurrentHashMap的做法实在是高。

第二个，get函数不加锁原因：

1.首先是table引用会涉及到线程间竞争问题。由于扩容会重新创建一个数组，所以原数组不会影响，而且get方法中首先会将全局引用赋值给tab局部引用，这一点也是可以保证线程安全的，另外table是volatile类型，保证线程可见性。

2.使用cas操作来获取头元素，线程安全，接下来是遍历链表/红黑树了，首先，Node中的value和next引用是volatile，保证线程可见性，然后分析put，remove对链表的遍历的影响：put时，直接将元素放入队尾，线程安全，remove时，设置pred.next=e.next，也不会造成线程安全问题。

PS:其实从ConcurrentHashMap使用synchronized关键字可以看出，并发包中不使用ReentrantLock，也能看出官方对于synchronized和ReentrantLock的态度，如果synchronized可以满足当前需求，就尽量选择synchronized。
