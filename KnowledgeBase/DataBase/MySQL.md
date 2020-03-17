
InnoDB存储引擎特点：支持事务，行锁，外键，默认非锁定读，主要面向OLTP方面的应用。



MySQL是如何保证在数据库发生异常情况下恢复到正常运行状态？



InnoDB的线程模型：
查看InnoDB状态的命令：show engine innodb status;

master thread：主线程
insert buffer thread：插入缓冲线程
log thread：日志线程
read thread/write thread：文件读写线程
lock monitor thread:锁监控线程
error monitor thread:错误监控线程


InnoDB的内存划分：

缓冲池，buffer pool
重做日志缓冲池：redo log buffer pool
额外内存池：additional memory pool

读操作：将数据库文件按页（每页16K）读取到缓冲池中，然后按最近最少使用LRU算法来保留缓存数据。
写操作：如果数据库文件需要修改，先修改缓冲池中的页，发生修改后，该页为脏页，然后再按照一定的频率将脏页刷新到文件中。

master thread运行过程：

	public void main(){
		while(true){
			for(int i=0;i<10;i++){
				//do 1 second work.
				Thread.sleep(1, TimeUnit.SECONDS);
			}
			//do 10 seconds work.
		}
	}

redolog的大小是固定的，在mysql中可以通过修改配置参数
innodb_log_files_in_group和innodb_log_file_size配置日志文件数量和每个日志文件大小，
redolog采用循环写的方式记录，当写到结尾时，会回到开头循环写日志。

有了redo log，当数据库发生宕机重启后，可通过redo log将未落盘的数据恢复，即保证已经提交的事务记录不会丢失。
























