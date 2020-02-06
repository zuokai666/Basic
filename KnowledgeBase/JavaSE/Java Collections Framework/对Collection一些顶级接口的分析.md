Collection接口是集合体系的顶级接口。一些集合允许重复元素，而另一些不允许，一些集合是有序的，一些是无序的。一些集合实现对他们的元素有限制。例如，一些实现禁止null元素。

	public interface Collection<E> extends Iterable<E> {
	    
	    // Modification Operations
	    boolean add(E e);//如果集合不允许重复而且已经含有元素，返回false
	    boolean remove(Object o);//只会删除一个
	    
	    // Bulk Operations
	    boolean containsAll(Collection<?> c);
	    boolean addAll(Collection<? extends E> c);
	    boolean removeAll(Collection<?> c);
	}
	
List：一个有序的集合。
不像是Set，List允许重复。


