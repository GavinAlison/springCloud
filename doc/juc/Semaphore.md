#Semaphore
juc中的同步信号量， 用来控制访问资源的线程数量。

## 应用场景:

限流，数据库连接池线程限制

## 数据结构

三个内部类、一些方法、一个属性sync

三个内部类是，一个AQS抽象队列同步器的子类sync，还有两个sync的子类，一个是公平的队列，一个是非公平的队列

一些方法， 是获取许可或者释放许可的方法

## 方法
```text
acquire()
获取一个令牌，在获取到令牌、或者被其他线程调用中断之前线程一直处于阻塞状态。

acquire(int permits)
获取一个令牌，在获取到令牌、或者被其他线程调用中断、或超时之前线程一直处于阻塞状态。

acquireUninterruptibly() 
获取一个令牌，在获取到令牌之前线程一直处于阻塞状态（忽略中断）。

tryAcquire()
尝试获得令牌，返回获取令牌成功或失败，不阻塞线程。
​
tryAcquire(long timeout, TimeUnit unit)
尝试获得令牌，在超时时间内循环尝试获取，直到尝试获取成功或超时返回，不阻塞线程。
​
release()
释放一个令牌，唤醒一个获取令牌不成功的阻塞线程。
​
hasQueuedThreads()
等待队列里是否还存在等待线程。
​
getQueueLength()
获取等待队列里阻塞的线程数。
​
drainPermits()
清空令牌把可用令牌数置为0，返回清空令牌的数量。
​
availablePermits()
返回可用的令牌数量。

```

## semaphore的实现原理

(1)、Semaphore初始化。

`Semaphore semaphore = new Semaphore(2);`

1.  new semaphore(2), 默认创建一个非公平的锁同步阻塞队列
2.  把初始令牌数量赋值给同步队列的state变量（状态）， state的值就是代表当前剩余的令牌数量

(2)、获取令牌

`semaphore.acquire();`

1、当前线程会尝试去同步队列获取一个令牌，获取令牌的过程也就是使用原子的操作去修改同步队列的state ,获取一个令牌则修改为state=state-1。

2、 当计算出来的state<0，则代表令牌数量不足，此时会创建一个Node节点加入阻塞队列，挂起当前线程。

3、当计算出来的state>=0，则代表获取令牌成功。

(3)、释放令牌
` semaphore.release();`

当调用 semaphore.release() 方法时

1、线程会尝试释放一个令牌，释放令牌的过程也就是把同步队列的state修改为state=state+1的过程

2、释放令牌成功之后，同时会唤醒同步队列的所有阻塞节共享节点线程

3、被唤醒的节点会重新尝试去修改state=state-1 的操作，如果state>=0则获取令牌成功，否则重新进入阻塞队列，挂起线程。

CountDownLatch通过计数器提供了比join更灵活的多线程控制方式，
CyclicBarrier也可以达到CountDownLatch的效果，而且有可复用的特点，
Semaphore则是采用信号量递增的方式，开始的时候并不需要关注需要同步的线程个数，并且提供获取信号的公平和非公平策略。


> https://zhuanlan.zhihu.com/p/98593407