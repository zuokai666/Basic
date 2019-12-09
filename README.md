日期：2019年12月9日
模块：数据库连接池
问题：因为复用连接，就会设计不同线程对同一个连接设置属性的不同，例如自动提交等等
解决：在最开始得到连接时，全部属性重置，考虑到后续可能的改动，也为了避免因添加功能而改动连接池代码，需要加入观察者模式来满足开闭原则。
关键词：设计模式
