# 问题：着重分析ConcurrentHashMap.put()的运行过程原理（2020年1月12日）。
答案：前提是单链表的长度不超过8，目前不讨论树化情况。

1.注意逻辑：onlyIfAbsent:只有缺少key的情况下设置value
	
	put(K key, V value) = putVal(key, value, false)//表明可以覆盖原值
	putIfAbsent(K key, V value) = putVal(key, value, true)//表明不可以覆盖原值
	
onlyIfAbsent在这里就是个开关，表示可不可以覆盖旧值。其中，putIfAbsent()经常用于保证多线程情况下设置同一key的值的唯一性。

2.判断key和value是否为空，抛出异常，表明ConcurrentHashMap策略是不允许key，value为null，区别于HashMap的允许为null的策略。

3.使用key的哈希值在重新计算哈希，使用高低十六位异或，降低冲突。

4.for死循环罩住整个逻辑：接下来来到第一处多线程竞争的逻辑，初始化Table，因为ConcurrentHashMap使用懒加载策略，所以这里既可以使用双重判断锁机制，又可以使用死循环+cas设置值操作。ConcurrentHashMap在这里使用的是第二种，效率较高。
	
	/**
     * Initializes table, using the size recorded in sizeCtl.初始化表，使用sizeCtl来记录使用的大小
     */
    private final Node<K,V>[] initTable() {
        Node<K,V>[] tab; int sc;
        while ((tab = table) == null || tab.length == 0) {
            if ((sc = sizeCtl) < 0)//为了防止多线程死循环占用CPU过高，使用yield线程让步，让出CPU，进行优化
                Thread.yield(); // lost initialization race; just spin
            else if (U.compareAndSwapInt(this, SIZECTL, sc, -1)) {//设置sizeCtl = -1，表明在初始化，上面的线程让步也是基于这个状态
                try {
                    if ((tab = table) == null || tab.length == 0) {//这里的if与上面的while进行双重判断，防止已经进行的线程拿到中间的状态。
                        int n = (sc > 0) ? sc : DEFAULT_CAPACITY;
                        @SuppressWarnings("unchecked")
                        Node<K,V>[] nt = (Node<K,V>[])new Node<?,?>[n];//默认初始化16大小的数组
                        table = tab = nt;
                        sc = n - (n >>> 2);//使用0.75的负载因子策略来设置，提高计算效率
                    }
                } finally {
                    sizeCtl = sc;//第一次，这里为16 - 16/2/2 = 12
                }
                break;
            }
        }
        return tab;
    }

5.首先判断头元素位置是否已经设置值。同样是取余法，使用(length - 1) & hash来提高效率，前提一定是length是2的倍数。使用2次cas操作来设置头元素，保证线程安全。

6.这时头元素已经存在，已经拿到了头元素的引用。判断f.hash == MOVED（从中文含义也能看出是移动的意思），如果是MOVED，表明当前数组正在扩容，则让该线程帮助转换，提高效率。原理就在helpTransfer()方法中。

7.接下来，使用synchronized锁住头元素，进行操作。首先使用tabAt(tab, i) == f来判断f还是不是当前头元素，双重判断。然后判断fistHash是否大于等于零。普通节点都会大于零，这里不分析红黑树。所以这里假设fistHash >= 0。观看下面代码可以看出，对于单链表的操作都会锁住头元素，所以对单链表的操作都会是线程安全的。

如果fistHash >= 0，表明是普通节点，单链表中的节点，接下来是常规操作了，如果key相同使用onlyIfAbsent来决定是否覆盖旧值，否则将新元素以Node的身份插入到单链表末尾。为何这里提到身份呢，因为Node拥有4种子类，每个代表特殊含义，包括Node本身的话一共有5种含义。在这里，我们可以看出，new Node，表明单链表插入元素使用Node种类。其中，binCount这个局部变量代表的含义是记录单链表的长度，用于下面的是否红黑树化的逻辑判断，但是从程序逻辑来看，binCount不等于单链表长度，而是等于单链表长度-1。

8.至此，上面锁住头元素的synchronized释放锁。接下来，判断binCount是否大于等于TREEIFY_THRESHOLD(8)，来决定树化。

9.最后最后一步，设置计数和一些其他操作。调用addCount(1L, binCount); PS：在第二次put相同index的元素时，其实单链表长度等于2了，但是这里的binCount=1，需要注意。

9.1.分析第一次put时，调用addCount(1L, 0)。
	
	private final void addCount(long x, int check) {
        CounterCell[] as; long b, s;
        if ((as = counterCells) != null ||第一次肯定为null，这里false
            !U.compareAndSwapLong(this, BASECOUNT, b = baseCount, s = b + x)) {
            
            //cas设置baseCount=baseCount+1，如果设置成功，则ture，前面在加！为false，所以这里的if逻辑不会走，但是实际baseCount已经设置成功了
            //如果竞争激烈，这里会设置失败，则会走if逻辑。（*未完后续*）
            
            CounterCell a; long v; int m;
            boolean uncontended = true;
            if (as == null || (m = as.length - 1) < 0 ||
                (a = as[ThreadLocalRandom.getProbe() & m]) == null ||
                !(uncontended =
                  U.compareAndSwapLong(a, CELLVALUE, v = a.value, v + x))) {//第一次as=null
                fullAddCount(x, uncontended);//走这里
                return;
            }
            if (check <= 1)
                return;
            s = sumCount();
        }
        if (check >= 0) {
            Node<K,V>[] tab, nt; int n, sc;
            while (s >= (long)(sc = sizeCtl) && (tab = table) != null &&      //sizeCtl寓意：调节大小的阈值
                   (n = tab.length) < MAXIMUM_CAPACITY) {//这里判断当前大小是否>=阈值，如果true，表明是时候该扩容了
                int rs = resizeStamp(n);
                if (sc < 0) {
                    if ((sc >>> RESIZE_STAMP_SHIFT) != rs || sc == rs + 1 ||
                        sc == rs + MAX_RESIZERS || (nt = nextTable) == null ||
                        transferIndex <= 0)
                        break;
                    if (U.compareAndSwapInt(this, SIZECTL, sc, sc + 1))
                        transfer(tab, nt);
                }
                else if (U.compareAndSwapInt(this, SIZECTL, sc,
                                             (rs << RESIZE_STAMP_SHIFT) + 2))
                    transfer(tab, null);//进入转换操作
                s = sumCount();
            }
        }
    }

9.2.分析transfer(tab, null)：
如果nextTab为null，创建一个2N的空Node数组并且赋值给nextTab。设置全局变量nextTable = nextTab（2N大小），transferIndex = N。所以扩容操作从一个nextTable变量开始了，并没有直接从原数组上操作。创建ForwardingNode节点，并将nextTable引入传入。

总结：
1.重点：在扩容时transfer方法中。大体思路是：将下一个扩容大小2N的空数组创建并传入ForwardingNode中，将元素复制到nextTable中，将旧数组的位置置为ForwardingNode。下面用伪代码来简单解释一下：
	
	synchronized (f) {//先锁住头元素，这样任何对单链表的操作都将同步，线程安全，例如保证扩容的同时不会有添加元素的情况.
		伪代码：
		about ln operation;
		about hn operation;//将单链表的元素转化为两条单链表，分别设置进行nextTab中。为何肯定是2条链?是因为和2有关系，不做解释
		
		setTabAt(nextTab, i, ln);//分别设置进行nextTab
	    setTabAt(nextTab, i + n, hn);//分别设置进行nextTab
	    setTabAt(tab, i, fwd);//设置本Table头元素为ForwardingNode，表明拆迁完毕，新人口已经复制到新家完毕
	}
	
当扩容结束时：
	
	nextTable = null;//设置空
    table = nextTab;//下一个数组正式登基
    sizeCtl = (n << 1) - (n >>> 1);//设置sizeCtl为扩容后的0.75
	
2.此次分析着重分析了两种类型Node与ForwardingNode，其余3种类型并没有涉及。