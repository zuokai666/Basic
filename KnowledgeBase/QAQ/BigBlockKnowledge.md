# 接口设计

# 分布式事务

# 分布式锁

# 数据库缓存双写一致性

# 分库分表

# 一致性哈希

1.计算服务器hash映射到0～2^32的圆上
2.计算数据hash映射到0～2^32的圆上
3.顺时针寻找第一个服务器，将数据存放到服务器中

优点：高扩展，低容错

缺点：数据不均匀
解决：增加虚拟节点

# 分布式唯一主键生成
