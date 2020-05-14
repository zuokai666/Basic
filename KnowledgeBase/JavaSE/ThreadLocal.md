# 看似线程安全，实则线程内部传递值

# 数据结构

java.lang.ThreadLocal.ThreadLocalMap<T> 哈希表
static class Entry extends WeakReference<ThreadLocal<?>> 弱引用元素
哈希函数：取模法
解决哈希冲突：开放地址法
数据扩容策略：双倍扩容










