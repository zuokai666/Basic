# 问题：着重分析ConcurrentHashMap.get()的运行过程原理（2020年1月12日）。
答案：前提是单链表的长度不超过8，目前不讨论树化情况。

1.开始分析：
	
	public V get(Object key) {
        Node<K,V>[] tab; Node<K,V> e, p; int n, eh; K ek;
        int h = spread(key.hashCode());
        if ((tab = table) != null && (n = tab.length) > 0 &&
            (e = tabAt(tab, (n - 1) & h)) != null) {//cas操作，保证获取头元素线程安全
            if ((eh = e.hash) == h) {
                if ((ek = e.key) == key || (ek != null && key.equals(ek)))//正好是头元素就太好了
                    return e.val;
            }
            else if (eh < 0)//表明当前节点不是正经节点，是过渡状态节点
                return (p = e.find(h, key)) != null ? p.val : null;//因为涉及到了4种不同子类重写了find，所以使用重写的find方法
            while ((e = e.next) != null) {//接下来是正经节点，遍历进行操作
                if (e.hash == h &&
                    ((ek = e.key) == key || (ek != null && key.equals(ek))))
                    return e.val;
            }
        }
        return null;
    }
    
2.目前只讨论ForwardingNode.find方法。这表明，数组正在扩容，而且当前index的单链表数据已经全部放入了nextTable中。所以，find的方法也只有从nextTable中遍历查找元素了。

疑问：
1：为何不是这么写呢？
	
	public V get(Object key) {
        Node<K,V>[] tab; Node<K,V> e, p; int n, eh; K ek;
        int h = spread(key.hashCode());
        if ((tab = table) != null && (n = tab.length) > 0 &&
            (e = tabAt(tab, (n - 1) & h)) != null) {//cas操作，保证获取头元素线程安全
            if ((eh = e.hash) == h) {
                if ((ek = e.key) == key || (ek != null && key.equals(ek)))//正好是头元素就太好了
                    return e.val;
            }
            return (p = e.find(h, key)) != null ? p.val : null;//因为如果eh > 0，就走Node本身的find方法，一样的逻辑呀。
        }
        return null;
    }
    
    
    
    
    