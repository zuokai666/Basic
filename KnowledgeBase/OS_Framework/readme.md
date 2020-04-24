# 服务注册中心，Eureka与Zookeeper比较

从实现思想考虑，E满足的是CAP中的AP，Z满足的是CAP中的CP。

注册中心节点集群，Z有分主从概念，当出现Master节点与其他节点失去联系后，剩余节点进行leader选举。这个也是在现实生活中很常见的场景。
在Z选举期间，注册中心是不可用的。

1.Eureka Server中没有主从概念，所有节点平等，相互之间复制来进行信息的同步。
2.Eureka Server自我保护机制，当15分钟内超过85%的节点没有收到心跳包，不在移除过期的节点，节点之间也不再复制了。
3.Eureka Client本地缓存


# Spring Cloud Netflix组件

服务发现（Eureka）
断路器（Hystrix）
智能路由（Zuul）
客户端负载平衡（Ribbon）


Hystrix：如果电灯泡的保险丝一样，当电流过大，保险丝熔断，可以起到保护电灯泡的作用，避免危险传递。

服务降级，我理解应该是接口层的逻辑，可以设计为注解@EnableServiceDegradation注释在方法上，使用AOP在接口最前面判断服务是否降级，如果是，直接返回失败。

熔断

限流：令牌桶算法






