# Selector selector = Selector.open();

TCP    127.0.0.1:49845        127.0.0.1:49846        ESTABLISHED     11664
TCP    127.0.0.1:49846        127.0.0.1:49845        ESTABLISHED     11664


异常居然由Selector.open()抛出，错误信息居然是Unable to establish loopback connection。

java.io.IOException: Unable to establish loopback connection
	at sun.nio.ch.PipeImpl$Initializer.run(PipeImpl.java:94)
	at sun.nio.ch.PipeImpl$Initializer.run(PipeImpl.java:61)
	at java.security.AccessController.doPrivileged(Native Method)
	at sun.nio.ch.PipeImpl.<init>(PipeImpl.java:171)
	at sun.nio.ch.SelectorProviderImpl.openPipe(SelectorProviderImpl.java:50)
	at java.nio.channels.Pipe.open(Pipe.java:155)
	at sun.nio.ch.WindowsSelectorImpl.<init>(WindowsSelectorImpl.java:127)
	at sun.nio.ch.WindowsSelectorProvider.openSelector(WindowsSelectorProvider.java:44)
	at java.nio.channels.Selector.open(Selector.java:227)
	at kb.io.nio.Test.main(Test.java:12)
Caused by: java.net.SocketException: No buffer space available (maximum connections reached?): connect
	at sun.nio.ch.Net.connect0(Native Method)
	at sun.nio.ch.Net.connect(Net.java:454)
	at sun.nio.ch.Net.connect(Net.java:446)
	at sun.nio.ch.SocketChannelImpl.connect(SocketChannelImpl.java:648)
	at java.nio.channels.SocketChannel.open(SocketChannel.java:189)
	at sun.nio.ch.PipeImpl$Initializer$LoopbackConnector.run(PipeImpl.java:127)
	at sun.nio.ch.PipeImpl$Initializer.run(PipeImpl.java:76)
	... 9 more



1）Windows下，Selector.open()会自己和自己建立两条TCP链接。不但消耗了两个TCP连接和端口，同时也消耗了文件描述符。

2）Linux下，Selector.open()会自己和自己建两条管道。同样消耗了两个系统的文件描述符。










