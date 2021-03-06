模板：<br/>
日期：<br/>
模块：<br/>
问题：<br/>
解决：<br/>
应用：<br/>
关键词：<br/>

日期：2019年11月20日<br/>
模块：分布式主键生成策略<br/>
问题：设置属性多，使用众多的set()方法不容易阅读<br/>
解决：使用建造者模式来解决<br/>
应用：com.zk.basic.id.impl.IdGenerateConfigRocket...set...check...fire<br/>
关键词：设计模式<br/>

日期：2019年11月20日<br/>
模块：分布式主键生成策略<br/>
问题：考虑到不同表可以使用不同的主键生成器，没必要所有只使用一个，扩大粒度，减少锁竞争冲突<br/>
解决：在不修改原有接口的基础上，使用继承扩展原有接口，增加我想要的功能，满足开闭原则，并且使用了组合设计模式<br/>
应用：IdGenerators extends IdGenerator<br/>
关键词：继承；设计模式<br/>

日期：2019年11月25日<br/>
模块：批量框架<br/>
应用：com.zk.basic.task.bootstrap.AbstractStep应用了StopWatch来计时<br/>
关键词：OOP<br/>

日期：2019年11月26日<br/>
模块：批量框架<br/>
问题：现有线程池满足不了数据传输的需求<br/>
解决：重写消费者线程池<br/>
应用1：com.zk.basic.task.consume.ThreadPoolConsumer<br/>
应用2：java.util.concurrent.ThreadFactory运用了简单工厂模式<br/>
关键词：线程池；设计模式<br/>

日期：2019年11月30日<br/>
模块：数据库连接池<br/>
问题：如何改变Connection的close()为回归连接池<br/>
解决：使用静态代理模式<br/>
应用：com.zk.basic.lamborghini.pool.ConnectionBean是容器中的元素，使用继承Connection接口来改变close方法<br/>
关键词：设计模式<br/>

日期：2019年12月1日<br/>
模块：模块容器<br/>
问题：随着[主键生成策略模块]和[数据库连接池模块]的建立，在使用[批量框架模块]的时候就会涉及到模块耦合问题，如何解耦模块就是个重要的问题<br/>
解决：加入中间层[模块容器:Summer]，将基础模块注入模块容器，在后续使用的时候通过高层次接口获取，实现了模块的随时替换<br/>
应用：Summer.rain().getBean(Interface.class)<br/>
关键词：解耦合<br/>

日期：2019年12月9日<br/>
模块：数据库连接池<br/>
问题：因为复用连接，就会设计不同线程对同一个连接设置属性的不同，例如自动提交等等<br/>
解决：在最开始得到连接时，全部属性重置，考虑到后续可能的改动，也为了避免因添加功能而改动连接池代码，需要加入观察者模式来满足开闭原则。<br/>
关键词：设计模式<br/>

日期：2019年12月9日<br/>
模块：批量框架<br/>
问题：没有做到一个步骤失败，整个任务停止，欠缺每一步的同步机制<br/>
解决：暂无<br/>
关键词：暂无<br/>

日期：2019年12月19日<br/>
模块：java.net.InetAddress<br/>
问题：域名解析的分析<br/>
分析：首先查找缓存，如果没有命中则查询域名服务器，其中涉及到缓存策略，cache有两个，即有效DNS解析的cache（addressCache）和无效DNS解析的cache（negativeCache）<br/>

InetAddressCachePolicy.NEVER 从不缓存
InetAddressCachePolicy.FOREVER 永远缓存
InetAddressCachePolicy.DEFAULT_POSITIVE 30s时间缓存

关键词：缓存与策略模式<br/>








