E:\software\redis>redis-cli -h 127.0.0.1 -p 6379
127.0.0.1:6379> hset user1 name zuokai
(integer) 1
127.0.0.1:6379> hset user1 age 25
(integer) 1
127.0.0.1:6379> hget user1 name
"zuokai"
127.0.0.1:6379> hget user1 age
"25"



string
list
hash
set
sorted set



# Redis如何设计分布式锁？
SET key value;将值关联到key上，如果存在覆盖旧值。显然达不到锁的独占的要求。
我们需要达到这样的效果：就像java.util.concurrent.ConcurrentHashMap.putIfAbsent(K, V)

synchronized(LOCK){
	if(GET key == null){
		SET key value;
		return "OK";
	}else {
		return "ERROR";
	}
}

所以使用SET key value NX
NX ：只在键不存在时，才对键进行设置操作。 NX：not exist，只有不存在时才会设置值，和putIfAbsent异曲同工之妙。

由于考虑到CAP理论中的P，持有锁的进程很有可能失去通信，导致锁无人释放，所以加入了Expire过期时间。

但是，又引入了一个新的问题，如果持有锁的正常情况下，锁过期怎么办？

命令 SET resource-name anystring NX EX max-lock-time 是一种在 Redis 中实现锁的简单方法。

客户端执行以上的命令：

如果服务器返回 OK ，那么这个客户端获得锁。
如果服务器返回 NIL ，那么客户端获取锁失败，可以在稍后再重试。
设置的过期时间到达之后，锁将自动释放。

可以通过以下修改，让这个锁实现更健壮：

不使用固定的字符串作为键的值，而是设置一个不可猜测（non-guessable）的长随机字符串，作为口令串（token）。
不使用 DEL 命令来释放锁，而是发送一个 Lua 脚本，这个脚本只在客户端传入的值和键的口令串相匹配时，才对键进行删除。
这两个改动可以防止持有过期锁的客户端误删现有锁的情况出现。

以下是一个简单的解锁脚本示例：

if redis.call("get",KEYS[1]) == ARGV[1]
then
    return redis.call("del",KEYS[1])
else
    return 0
end

value 的值设置为随机数主要是为了更安全的释放锁，释放锁的时候需要检查 key 是否存在，且 key 对应的值是否和我指定的值一样，是一样的才能释放锁。
所以可以看到这里有获取、判断、删除三个操作，为了保障原子性，我们需要用 lua 脚本。

# 怎么解决节点挂掉导致的不可用问题

Redis集群方式共有三种：主从模式，哨兵模式，cluster(集群)模式

主从模式会保证数据在从节点还有一份，但是主节点挂了之后，需要手动把从节点切换为主节点。它非常简单，但是在实际的生产环境中是很少使用的。
哨兵模式就是主从模式的升级版，该模式下会对响应异常的主节点进行主观下线或者客观下线的操作，并进行主从切换。它可以保证高可用。
cluster (集群)模式保证的是高并发，整个集群分担所有数据，不同的 key 会放到不同的 Redis 中。每个 Redis 对应一部分的槽。



# redis 的过期策略

常见问题：

往 redis 写入的数据怎么没了？
数据明明过期了，怎么还占用着内存？

redis 过期策略是：定期随机删除 + 惰性删除 + 内存淘汰策略(ALLKEYS-LRU)

这与JVM GC有很大的区别。

# 引入Redis带来的问题

###### 缓存与数据库双写一致性问题

强一致性：禁止使用缓存
最终一致性_1：设置过期时间
最终一致性_2：先更新数据库，再删缓存。其次，因为可能存在删除缓存失败的问题，提供一个补偿措施即可，例如利用消息队列。

###### 缓存击穿

并发用户查询一条数据：

解决方案:
热点数据永不过期

###### 缓存雪崩

缓存大面积同时过期，导致压力直接怼到了DB上。

解决方案:
设置随机时间

###### 缓存穿透

查询大量不存的key，导致压力直接怼到了DB上。

解决方案:
1.使用BloomFilter过滤，如果不存在，那就一定不存在。
2.当查询到不存在的key时，也放入到缓存中即可。















