# MySQL中锁与Java中的Lock异同性分析

1.MySQL行锁的类型
	
	共享锁 S Lock：允许事务读一行数据
	排他锁 X Lock：允许事务删除或者更新一行数据
	
与java.util.concurrent.locks.ReentrantReadWriteLock原理相同，只有读+读可以共存。

2.如果一个事务读加了排它锁，另一个事务一样，会造成线程阻塞，但是不是无限制阻塞，超过innodb_lock_wait_timeout时间设置后会抛出异常

与上面中的Lock.tryLock(time)相同
	
	int innodb_lock_wait_timeout = 90;
	if(lock.tryLock(innodb_lock_wait_timeout, TimeUnit.SECONDS)){
		
	}else {
		//锁超时
	}
	
3.非锁定读，默认情况下读不加锁，基于历史快照数据的方式，多版本并发控制MVCC来提高并发性。

与CopyOnWriteArrayList原理相同。



PS：

J.U.C中Lock接口的定义：
	
	public interface Lock {
		
	    void lock();
	    void lockInterruptibly() throws InterruptedException;//与第一个相同，只是线程中断问题，不分析
	    boolean tryLock();
	    boolean tryLock(long time, TimeUnit unit) throws InterruptedException;
	    void unlock();
	    Condition newCondition();//条件队列，不分析
	}
