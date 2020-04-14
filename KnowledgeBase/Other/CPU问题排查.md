
1.执行“top”命令：查看所有进程占系统CPU的排序。极大可能排第一个的就是咱们的java进程（COMMAND列）。PID那一列就是进程号。

2.执行“top -Hp 进程号”命令：查看java进程下的所有线程占CPU的情况。

3.执行“printf "%x\n 10"命令 ：后续查看线程堆栈信息展示的都是十六进制，为了找到咱们的线程堆栈信息，咱们需要把线程号转成16进制。例如, printf "%x\n 10"   -》打印：a，那么在jstack中线程号就是0xa.

4.执行 “jstack 进程号 | grep 线程ID” 查找某进程下  -》线程ID（jstack堆栈信息中的nid）=0xa的线程堆栈信息。如果“"VM Thread" os_prio=0 tid=0x00007f871806e000 nid=0xa runnable”，第一个双引号圈起来的就是线程名，如果是“VM Thread”这就是虚拟机GC回收线程了

5.执行“jstat -gcutil 进程号 统计间隔毫秒 统计次数（缺省代表一致统计）”，查看某进程GC持续变化情况，如果发现返回中FGC很大且一直增大-》确认Full GC! 也可以使用“jmap -heap 进程ID”查看一下进程的堆内从是不是要溢出了，特别是老年代内从使用情况一般是达到阈值(具体看垃圾回收器和启动时配置的阈值)就会进程Full GC。

6.执行“jmap -dump:format=b,file=filename 进程ID”，导出某进程下内存heap输出到文件中。可以通过eclipse的mat工具查看内存中有哪些对象比较多。


top -Hp pid 显示所有的线程