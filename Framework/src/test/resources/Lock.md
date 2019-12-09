锁优化：
自旋锁
互斥同步对性能最大的影响是阻塞的实现，挂起线程与恢复线程都需要用户态转入内核态，但是很多情况下共享数据的锁定状态只会持续很短的时间，为了这点等待就挂起得不偿失，
因此使用自旋，依然处于用户态，消耗的只不过是一点CPU，就可以获取锁.

JDK 1.6 默认开启自旋锁
-XX:UseSpinning

自旋等待虽然避免了线程切换的开销，但是占用CPU，使CPU空转。
设想的情况自旋时间短，然后获取到锁。因此自旋时间应该有一定的限度。
自旋次数的默认值10次，用户可以使用-XX:PreBlockSpin来更改。

简直就是Spring.Retry重试策略的翻版。

JDK 1.6 引入自适应自旋锁


轻量级锁

偏向锁




可重入锁：
void lock();
如果锁还没有被另一个线程获取，那就获取锁并且立即返回，设置这个锁的计数从0到1。
如果当前线程已经获取锁，计数+1，并且立即返回。
如果锁被其它线程持有，当前线程进行阻塞，LockSupport.park()，线程进入Waiting状态。





Semaphore信号量
类结构关系：
java.util.concurrent.Semaphore与AbstractQueuedSynchronizer基于组合合作
意图权限控制，限制获取某种资源的线程数量。


java.util.concurrent.locks.AbstractQueuedSynchronizer
Node head;等待队列的头
Node tail;等待队列的尾
int state;同步状态
Node：双向链表


所谓的排他，共享的区别？


所谓的公平，不公平的区别？
在获取许可前，先看看有没有人线程在自己之前排队










