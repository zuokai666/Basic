
com.zk.basic.beans.factory.Summer
主要职责提供其它高级接口的具体实现，使得其它模块不依赖与具体实现，只依赖于Summer和接口。
好处：可以随时替换实现

com.zk.basic.lamborghini.LamborghiniDataSource
兰博基尼数据库连接池

com.zk.basic.id.IdGenerators
分布式主键生成策略

com.zk.basic.task.bootstrap.SimpleJobLauncher
批量处理框架，主要用于多线程读文件导入数据库，多线程读数据库导出文件，可以做到10W数据花费10s导入。


----------------------------------------模块分割线---------------------------------------------

连接池介绍：
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

