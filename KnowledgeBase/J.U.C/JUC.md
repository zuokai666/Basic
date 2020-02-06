# 问题：synchronized与ReentrantLock的区别，我们又应该作何选择呢？
答案：
相同：
原子性，可见性，有序性，可重入性
不同：
1.synchronized是非公平锁，ReentrantLock可以创建公平与非公平锁。
2.synchronized无法中断一个正在等待获取锁的线程，ReentrantLock使用lockInterruptibly可以感知中断获取锁的操作。
3.ReentrantLock可以实现非块结构的加锁。
4.ReentrantLock使用tryLock(time)来实现定时锁，也可避免死锁。
5.在实现生产者消费者模型时，synchronized加锁，只能唤醒所有等待锁的线程Object.notifyAll()。ReentrantLock可以使用condition减小等待锁的粒度，在唤醒线程时就可以针对条件队列来唤醒线程了Condition.signal()，提高效率。
6.在等待锁时，使用synchronized的线程进入的是BLOCKED状态，使用ReentrantLock的线程进入的是WAITING状态。

选择：
1.ReentrantLock并不是一种替代内置锁的方法，而是当内置加锁机制不适用时，作为一种可选择的高级功能。
2.Java5时，ReentrantLock比内置锁可以提供更好的竞争性能，但在Java6后，使用了改善后的算法来管理内置锁，导致两者的差异越来越小，所以还是优先使用内置锁，仅当内置锁不能满足要求时，才可以考虑使用ReentrantLock。

PS：
1.在公平的锁上，线程将按照它们发出请求的顺序来获得锁，但在非公平的锁上，则允许插队：当一个线程请求非公平锁时，如果在发出请求的同时该锁状态变为可用，那么这个线程将跳过队列中所有等待线程并获得这个锁。拿一个生活中的场景举例子，在我晚上经常去的一家肉饼店，肉饼出的很慢，我们都需要付钱拿号等待，结果有个顾客过来点餐，他正好点到肉饼的时候肉饼出锅了，如果是非公平的场景，店员无视我们前面等待的顾客，就直接把肉饼给这位顾客了。
2.产生死锁的4个必要条件：互斥条件；请求与保持条件；不剥夺条件；循环等待条件；tryLock破坏了不剥夺条件