# Eureka 服务注册

从Jar包上可以看出，Eureka体系分为Client-Server体系，想必这两个包都自己的专属职责
	
	eureka-client-1.9.3.jar
	eureka-core-1.9.3.jar

### Eureka Client 四大职责

	a) Registering the instance with Eureka Server 携带自己的信息注册到中心
	b) Renewal of the lease with Eureka Server 更新租约(30s)
	c) Querying the list of services/instances registered with Eureka Server 查询注册到中心的列表，用于本地缓存，满足AP理论
	d) Cancellation of the lease from Eureka Server during shutdown 注册中心下线

### Eureka Client 领域模型(从职责，也可以说是需求中总结出来)

	实例信息与租约信息(贫血数据对象)
	DiscoveryClient(客户端逻辑的发起者，拥有上述四大职责)(管理者)
	EurekaHttpClient(真正发起HTTP请求的对象)(使用Jersey RESTful框架来发送请求)

### Eureka Server 职责
	
	作为服务器，监听端口，提供注册，更新租约，查询，下线接口(多级缓存，提高并发性)
	服务器集群间的复制(提高可用性)
	定时剔除服务及自我保护机制(90s)

### Eureka Server Self Preservation

Eureka Server在运行期间会去统计心跳失败比例在15分钟之内是否低于85%，如果低于 85%，Eureka Server 会将这些实例保护起来，让这些实例不会过期.


---

@ create_time 2020年5月13日

@ update_time 2020年5月13日

---


