500W 435s
10W 10s
100W 100s


com.zk.basic.lamborghini是数据库连接池包

采用3层类来实现功能：
LamborghiniDataSource
对外表现DataSource，并只提供getConnection方法

LamborghiniPool
这一层开始依赖数据源和连接和连接代理。使用ConcurrentBag对连接进行管理，提供了定时器功能来监控连接的状态
并且开始缩小权限，只对外提供借走，归还功能，定时器来直接操作ConcurrentBag的添加，删除功能
getConnection()使用Semaphore来限制访问方法的线程数

ConcurrentBag
提供了一个基本容器功能，有添加，删除，借走，归还功能，只依赖于BagBean。不过这是个死物，还需要外部来控制


疑问：
当获取连接超时，如何返回呢？可以看看HikariCP的处理。


Semaphore信号量








