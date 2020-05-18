# Hystrix 断路器

# 策略

# 默认失败回调

circuitBreaker.errorThresholdPercentage
错误率，默认值50%，例如一段时间（10s）内有100个请求，其中有54个超时或者异常，那么这段时间内的错误率是54%，大于了默认值50%，这种情况下会触发熔断器打开。

circuitBreaker.requestVolumeThreshold

默认值20。含义是一段时间内至少有20个请求才进行errorThresholdPercentage计算。比如一段时间了有19个请求，且这些请求全部失败了，错误率是100%，但熔断器不会打开，总请求数不满足20。

circuitBreaker.sleepWindowInMilliseconds

半开状态试探睡眠时间，默认值5000ms。如：当熔断器开启5000ms之后，会尝试放过去一部分流量进行试探，确定依赖服务是否恢复。

# 熔断思路

熔断针对的是IP级别，而不是微服务级别。

从最开始系统启动，统计对IP级别的每秒的请求数(成功/失败)，共存储15秒的数据。

HashMap<IP, LinkedList<RequestInfo>(15)>

class RequestInfo {
	
	String ip;// ip地址
	int totalCount;// 总请求次数
	int successCount;// 成功请求次数
	int failCount;// 失败请求次数
}

初始请求次数：circuitBreaker.requestVolumeThreshold
判断熔断的最少请求数，默认是20；只有在一个统计窗口内处理的请求数量达到这个阈值，才会进行熔断与否的判断


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

每10秒的窗口期内，当请求次数超过20次，且出错比例超过50%，则触发熔断器打开
当熔断器5秒后，会尝试放过去一部分流量进行试探











