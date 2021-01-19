# spring boot

1.  自动装配@EnableAutoConfiguration， @ImportSelector, spring.factories, @Import
2.  内嵌Web 服务， tomcate,jetty, undertow
3.  配置文件, configuration, 优先级，@ConfigurationProperties， 多环境配置profiles
    @EnableConfigurationProperties(*.class),@Configuration,
4.  热部署spring-boot-devtools
5.  监控，actuator,spring-boot-starter-actuator
6.  统一异常处理@ControlerAdvice,@ExceptionHandler
7.  异步执行@Async, 需要重新定义线程池 AsyncConfigurer, Executor, AsyncUncaughtExceptionHandler
8.  事件广播机制ApplicationEvent,ApplicationEventPublisherAware, ApplicationListener,@EventListener
9.  随机端口,编译打包,spring-boot-maven-plugin
10. @Configuration,@Bean, ConfigurationClassPostProcessor


# spring boot starter
1.  自动装配， spring.factories , EnableAutoConfiguration,@Import,
2.  配置文件，@ConfigurationProperties(prefix=""), @Configuration, @EnableConfigurationProperties(UserPorperties.class)
    @Bean, @ConditionalOnProperty, @@ConditionalOnBean, @ConditionalOnClass, @ConditionalOnXX

# spring cloud
1.  eureka 注册中心， @EnableEurekaServer
2.  @EnableDiscoveryClient
3.  @EnableWebSecurity,WebSecurityConfigurerAdapter
4.  服务上下线监控, EurekaInstanceCanceledEvent 服务下线事件。
             EurekaInstanceRegisteredEvent 服务注册事件。
             EurekaInstanceRenewedEvent 服务续约事件。
             EurekaRegistryAvailableEvent Eureka 注册中心启动事件。
             EurekaServerStartedEvent Eureka Server 启动事件。
5.  @LoadBalanced原理, LoadBalancerAutoConfiguration
    逻辑就是给 RestTemplate 增加拦截器，在请求之前对请求的地址进行替换，或者根据具体的负载策略选择服务地址，然后再去调用
    如何往RestTemplate 注入拦截器，http://c.biancheng.net/view/5348.html， ClientHttpRequestInterceptor
6.  LoadBalanced的种类，RandomRule， RoundRobinRule， RetryRule， WeightedResponseTimeRule
    自定义，implements IRule 
7.  Feign, @EnableFeignClients,开启自动化装配feign，会扫描对应的@FeignClient注解对应的类，将其注册到spring 容器中，
    在调用的时候，通过动态代理，生成代理类，进行调用方法，解析方法上面的Url，解析方法的参数，通过编码成指定的字符，然后通过负载均衡调用对应的方法。
8.  feign的配置：Basic 认证配置，超时时间配置， 客户端组件配置，GZIP压缩配置，编码器解码器配置，拦截器，日志等级，
9.  Hystrix隔离、限流和降级,隔离策略isolationStrategy包括信号量和线程池
    https://www.cnblogs.com/xiong2ge/p/hystrix_faststudy.html
10. Hystrix整体的处理流程,Hystrix框架通过命令模式来实现方法粒度上的服务保障，主要涉及HystrixCommand和HystrixObservableCommand类，
前者提供同步的execute和异步的queue方法，后者提供立即执行observe和延迟执行toObservable的回调方法。此外，实际项目中通常不会使用Hystrix集成的本地缓存。
11. zuul, 网关， 路由, 请求监控, 认证鉴权, 压力测试, 灰度发布, 使用一些zuulFilter对其进行过滤与拦截， pre,post,route,error
12. gateway , 同上
13. 链路跟踪zipkin, https://blog.51cto.com/10705830/2433647?source=drt
    spring-cloud与zipkin结合，https://blog.csdn.net/forezp/article/details/76795269
14. zipkin是Twitter开源的分布时追踪系统，可接收数据，存储数据（内存/cassandra/mysql/es），检索数据，展示数据，
    他本神不会直接在分布式的系统服务种trace追踪数据，可便捷的使用sleuth来收集传输数据。
    
    sleuth是spring cloud的分布式跟踪工具，主要记录链路调用数据，本身只支持内存存储，在业务量大的场景下，
    为拉提升系统性能也可通过http传输数据，也可换做rabbit或者kafka来传输数据。    
15. 链路的追踪原理：
    跟踪器位于应用程序中，记录发生的操作的时间和元数据，收集的跟踪数据称为Span，将数据发送到Zipkin的仪器化应用程序中的组件称为Reporter，
    Reporter通过几种传输方式（http，kafka）之一将追踪数据发送到Zipkin收集器(collector)，然后将跟踪数据进行存储(storage)，由API查询存储以向UI提供数
16. 

