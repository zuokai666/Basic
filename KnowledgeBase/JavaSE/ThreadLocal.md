# 看似线程安全，实则线程内部传递值

# 数据结构

java.lang.ThreadLocal.ThreadLocalMap<T> 哈希表
static class Entry extends WeakReference<ThreadLocal<?>> 弱引用元素
哈希函数：取模法
解决哈希冲突：开放寻址法
数据扩容策略：双倍扩容

# 使用场景

1.线程内部传递值，避免参数过度传递
2.避免线程竞争，例如：SimpleDateFormat(线程不安全)，Random(线程安全，但是竞争大性能降低)

# 内存泄漏

惰性删除策略

# 常用方法

set

get：expungeStaleEntry(i)

remove：expungeStaleEntry(i)



