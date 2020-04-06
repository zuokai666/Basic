# 墨者安全分享：Linux系统怎么抵御SYN、DDOS攻击？

墨者安全科技

发布时间：18-09-2511:24
SYN攻击是现在最常见的DDoS攻击之一，这是一种利用TCP/IP协议3次握手的原理，发送大量的建立连接的网络包，但不实际建立连接，最终导致被攻击服务器的网络队列被占满，无法被正常用户访问。Linux服务器在运营过程中遭到SYN,DDOS攻击时，最直接的方法就是接入墨者安全高防云防御或添置硬件防火墙。有些企业可能觉得成本太高，那也可以考虑利用Linux 系统本身提供的防火墙功能配置来防御。



一、添加以下参数

二、参数详解

1、net.ipv4.tcp_synack_retries = 0

表示回应第二个握手包（SYN+ACK包）给客户端IP后，如果收不到第三次握手包（ACK包）后，不进行重试，加快回收“半连接”，不要耗光资源。不修改这个参数，模拟攻击，10秒后被攻击的80端口即无法服务，机器难以ssh登录； 用命令 netstat -na | grep SYN_RECV检测“半连接”hold住180秒；


2、net.ipv4.tcp_syn_retries = 0

默认是5，当没有收到服务器端的SYN+ACK包时，客户端重发SYN握手包的次数；


3、net.ipv4.tcp_max_syn_backlog = 20480

半连接队列长度，增加SYN队列长度到20480：加大SYN队列长度可以容纳更多等待连接的网络连接数，具体多少数值受限于内存。


4、fs.file-max = 819200

系统允许的文件句柄的最大数目，因为连接需要占用文件句柄；


5、net.core.somaxconn = 65536

用来应对突发的大并发connect请求；


6、net.core.wmem_max = 16777216

最大的TCP数据发送缓冲（字节）


7、net.core.netdev_max_backlog = 165536

网络设备接收数据包的速度比内核处理这些包的速度快时，允许送到队列的数据包的最大数目；


8、net.ipv4.ip_local_port_range = 10000 65535

本机主动连接其他机器时的端口分配范围，比如说，在vdftpd主动模式会用到，如果只是开启22端口，不会使用到net.ipv4.ip_local_port_range这个功能；


9、net.ipv4.tcp_syncookies = 1

表示开启SYN Cookies，当出现半链接队列溢出时启用cookies来处理，调大半链接队列，可防范少量SYN攻击，默认为0，改为1开启；


10、net.ipv4.tcp_tw_reuse = 1

表示开启tcp连接重用，允许将TIME-WAIT sockets重新用于建立新的tcp连接，默认为0，改为1开启；


11、net.ipv4.tcp_tw_recycle = 1

表示开启tcp连接中TIME-WAIT sockets的快速回收，默认为0，改为1开启；


12、net.ipv4.tcp_fin_timeout = 10

对于本端断开的socket连接，tcp保持在FIN_WAIT_2状态的时间。



这些内核配置参数应该在每台服务器上线之前配置好，可以有效的防御小流量DDoS攻击。当遇到大流量DDoS洪水攻击时，还是要靠接入墨者安全高防云防御或添置硬件防火墙才能有效防御，企业可以根据自身实际情况来选择合适的防御方案。
