AQS是一个类，使用了模板设计模式，定义了算法的骨架，子类只需要实现自己的方法就可以。

AQS有4个属性
	
	等待队列的头
	等待队列的尾
	独占锁的线程
	同步状态state
	
acquire(int arg):
	
	tryAcquire：会尝试再次通过CAS获取一次锁
	addWaiter：通过自旋CAS，将当前线程加入上面锁的双向链表（等待队列）中
	acquireQueued：通过自旋，判断当前队列节点是否可以获取锁





