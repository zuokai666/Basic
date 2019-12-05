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



