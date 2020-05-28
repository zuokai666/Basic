
Random：线程安全，高并发会带来性能损失，建议一个线程使用一个ThreadLocal<Random>
弱随机

SecureRandom：建议一个线程使用一个ThreadLocal<SecureRandom>
强随机

ThreadLocalRandom



