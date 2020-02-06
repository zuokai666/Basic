# 问题：着重分析ConcurrentHashMap的线程安全原理
答案：

2020年1月10日
2020年2月2日 重新分析

换个思路分析，从ConcurrentHashMap的属性和节点类型来思考一下为什么?
	
	int MOVED = -1;//Forwarding node的哈希值
	int TREEBIN = -2;//红黑树的根节点的哈希值
	int RESERVED = -3;//临时保留的哈希值                   ------ reserved 保留; 贮备; 拥有，保持;------

这三个哈希值都是负值，函数中有很多情况都会涉及到。下面列出涉及到的函数：

	V get(key);//拿到头元素后首先判断头元素的哈希值是否小于零
	V putVal();//判断头元素的哈希值是否等于MOVED，后面会判断哈希值大于等于零
	remove();//判断哈希值大于等于零
	clear();//判断哈希值大于等于零
	ForwardingNode.find();//判断元素的哈希值是否小于零
	transfer();//判断头元素的哈希值是否等于MOVED，后面会判断哈希值大于等于零

再接着分析，重要成员：Node，一个单链表的表示
子类会有一个负值的哈希值拥有特殊的含义，并且包含着空的键值对，但是不会导出来。其中有个find的方法：
	
	/**
     * Virtualized support for map.get(); overridden in subclasses.
     * 为get方法进行服务，经常会被子类重写。
     */
    Node<K,V> find(int h, Object k) {
        //遍历单链表，判断hash相等与否，在判断equals是否相等。
    }

列出Node的继承体系：

* ForwardingNode<K, V>
* ReservationNode<K, V>
* TreeBin<K, V>
* TreeNode<K, V>

ForwardingNode:在转换过程中，插入到头的节点，继承自Node节点，体现了继承的好处，可以在不改变类的前提下增加功能，扩展属性。创建的时候指定了hash，其中key，value，next都是为null。其中，有一个nextTable的属性，并且重写了父类的find方法。

由源码得知，当设置旧数组的桶的首元素为MOVED时，已经将数组当前的桶的元素转移完毕了。
	
    setTabAt(nextTab, i, ln);
    setTabAt(nextTab, i + n, hn);
    setTabAt(tab, i, fwd);
	
当新增元素时，检查到自己位置的首元素是MOVED，就去参与转移工作。如果数组在转移，还未到当前位置，则不需要参与转移。
当多线程前来put元素时，多线程会参与旧数组到扩容后的新数组的数据转移工作，提高效率，因此这里是欢迎多线程的，在一定程度上，线程越多，转移效率越高。
	
	if ((fh = f.hash) == MOVED){
		tab = helpTransfer(tab, f);
	}
	







