# Hystrix 断路器

# 配置属性

com.netflix.hystrix.HystrixCommandProperties

//时间窗（ms）
static final Integer default_metricsRollingStatisticalWindow = 10000;
//最少请求次数
private static final Integer default_circuitBreakerRequestVolumeThreshold = 20;
//熔断器打开后开始尝试半开的时间间隔
private static final Integer default_circuitBreakerSleepWindowInMilliseconds = 5000;
//错误比例
private static final Integer default_circuitBreakerErrorThresholdPercentage = 50;


# 状态

关闭，开启，半开

# 运行原理

每10秒的窗口期内，当请求次数超过20次，且出错比例超过50%，则触发熔断器打开
当熔断器5秒后，会尝试放过去一部分流量进行试探

# 资源隔离

线程池(异步)与信号量(同步)








QPS（TPS）= 并发数 / 平均响应时间


