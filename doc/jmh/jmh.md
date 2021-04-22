#jmh 基准测试
## 什么是基准测试？
简单点说，就是我们看到或听到很多人说什么方式去使用Java的性能

影响基准测试的因素比较多，包括代码预热、编译器动态优化、资源回收（GC）、文件缓存、电源、其他程序，JVM的VM选项等等。我们在进行基准测试前需要关注其对产生结果的影响。


## JMH是什么？
JMH是OpenJDK提供的基准测试工具，是由Oracle实现JIT的相同人员开发的。

## 怎么使用？
maven
```
<!-- https://mvnrepository.com/artifact/org.openjdk.jmh/jmh-core -->
<dependency>
    <groupId>org.openjdk.jmh</groupId>
    <artifactId>jmh-core</artifactId>
    <version>1.20</version>
</dependency>
 <dependency>
     <groupId>org.openjdk.jmh</groupId>
     <artifactId>jmh-generator-annprocess</artifactId>
     <version>1.20</version>
</dependency>
```

## 添加注解
@Benchmark

#### @BenchmarkMode(Mode.All)

Mode有：- Throughput: 整体吞吐量，例如“1秒内可以执行多少次调用” (thrpt,参加第5点)
- AverageTime: 调用的平均时间，例如“每次调用平均耗时xxx毫秒”。（avgt）
- SampleTime: 随机取样，最后输出取样结果的分布，例如“99%的调用在xxx毫秒以内，99.99%的调用在xxx毫秒以内”（simple）
- SingleShotTime: 以上模式都是默认一次 iteration 是 1s，唯有 SingleShotTime 是只运行一次。往往同时把 warmup 次数设为0，用于测试冷启动时的性能。（ss）

#### @OutputTimeUnit(TimeUnit.MILLISECONDS)
统计单位， 微秒、毫秒 、分、小时、天

#### @State
可参：JMHFirstBenchmark.java
 
类注解，JMH测试类必须使用@State注解，State定义了一个类实例的生命周期，可以类比Spring Bean的Scope。由于JMH允许多线程同时执行测试，不同的选项含义如下：

```
Scope.Thread：默认的State，每个测试线程分配一个实例；
Scope.Benchmark：所有测试线程共享一个实例，用于测试有状态实例在多线程共享下的性能；
Scope.Group：每个线程组共享一个实例；
```
#### @Benchmark 
很重要的方法注解，表示该方法是需要进行 benchmark 的对象。和@test 注解一致

####  @Setup 
方法注解，会在执行 benchmark 之前被执行，正如其名，主要用于初始化。

#### @TearDown (Level)
方法注解，与@Setup 相对的，会在所有 benchmark 执行结束以后执行，主要用于资源的回收等。
   (Level)   用于控制 @Setup，@TearDown 的调用时机，默认是 Level.Trial。
          
          Trial：每个benchmark方法前后；
          Iteration：每个benchmark方法每次迭代前后；
          Invocation：每个benchmark方法每次调用前后，谨慎使用，需留意javadoc注释；  
#### @Param
@Param注解接收一个String数组 ，
可以用来指定某项参数的多种情况。特别适合用来测试一个函数在不同的参数输入的情况下的性能。
 可参：JMHFirstBenchmark.java
 
#### Options常用选项
-   include 
benchmark 所在的类的名字，这里可以使用正则表达式对所有类进行匹配。
-   fork
JVM因为使用了profile-guided optimization而“臭名昭著”，这对于微基准测试来说十分不友好，因为不同测试方法的profile混杂在一起，“互相伤害”彼此的测试结果。对于每个@Benchmark方法使用一个独立的进程可以解决这个问题，这也是JMH的默认选项。注意不要设置为0，设置为n则会启动n个进程执行测试（似乎也没有太大意义）。
fork选项也可以通过方法注解以及启动参数来设置。
-   warmupIterations
预热次数，每次默认1秒。
-   measurementIterations 
实际测量的迭代次数，每次默认1秒。
-   Group
方法注解，可以把多个 benchmark 定义为同一个 group，则它们会被同时执行，譬如用来模拟生产者－消费者读写速度不一致情况下的表现。
-   Threads
每个fork进程使用多少条线程去执行你的测试方法，默认值是Runtime.getRuntime().availableProcessors()。 

#### 输出结果 

          


