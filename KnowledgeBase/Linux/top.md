# top命令

### -d

代表秒数，表示top命令显示的页面更新一次的间隔

### -n

次数

### -p

默认进入top时，各进程是按照CPU的占用量来排序的。

在top基本视图中，按键盘数字“1”可以监控每个逻辑CPU的状况

# -H 显示线程信息，-p指定pid

top -Hp pid可以查看某个进程的线程信息

# 思路

top -c 查看CPU 占用情况

top -Hp [pid] 定位该进程内所有的线程使用情况，检测到CPU高的线程ID

jstack [pid] > pid.stack 文件分析，拿上面的ID进行分析












