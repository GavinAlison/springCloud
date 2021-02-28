# java 并发编程
7大类, 1.atomic, 2.lock, 3.AQS同步器，4.AQS的实现类--计数器、信号量、栅栏，5.同步队列，6.线程池，7.并发集合
-   atomic原子类
-   lock
-   executor
-   同步队列
-   线程池
-   计时器
-   同步器
-   并发集合

```text
atomic: atomicLong
lock: synchronized 和 reentrantlock , readlock, writelock,
AQS: Semaphore, CountdownLatch, ReentrantLock, CyclicBarrier
同步队列: BlockingQueue, PriorityBlockingQueue
线程池: 5中, SingletonThreadExecutor, FixedExecutor, CachedExceutor, ScheduleExecutor, Forkjoinpool
并发集合: ConcurrentHashMap, CopyOnWriteArratList
```

## atomic
```text
AtomicBoolean
AtomicInteger
AtomicIntegerArray
AtomicIntegerFieldUpdater
AtomicLong
AtomicLongArray
AtomicLongFieldUpdater
AtomicMarkableReference
AtomicReference
AtomicReferenceArray
AtomicReferenceFieldUpdater
AtomicStampedReference
DoubleAccumulator
DoubleAddr
LongAccumulator
LongAdder
Striped64
```

原子类的原子性，是通过CAS自旋实现的。

项目中使用的地方： 统计多个线程处理的数据量，需要使用AtomicLong进行incrementAndGet +1

## lock
synchronized 和 lock 锁

### synchronized 
在java1.7之后做了修改，原先是通过锁对象或者锁方法进行锁块，粒度比较大，
实现原语monitor锁，可以对class文件进行反编译，查看到锁块。

1.7之后进行轻量锁--》 偏向锁--》自旋锁--》对象锁 的逐级过度的过程

项目中使用的Synchronized 的地方是， 消费消息的时候，需要Synchronized进行锁定对象， 这个对象属于当前线程的对象thread.  Object

### lock
实现类： ReentrantLock 
底层通过AQS 同步队列来实现的， ReentrantLock包含fairSync和NonFairSync
ReentrantLock默认使用非公平锁，也可以通过构造器来显示的指定使用公平锁。
fairSync 与 nonfairSync的区别在于，
 判断当前 状态是否可以获取锁, state = 0 , 如果可以获取锁，
 那就判断是否在队列中，并且是队首，获取锁，并修改状态值state。
如果当前状态state != 0 ， 判断lock持有的线程是否是当前线程，是的话，设置lock 的state 的值，
 state+ acquires, 返回true， 表示获取到了锁。


AQS, 同步队列，  维护了一个同步状态state来计数重入次数，state初始值为0。维持一个线程， 表示获得锁的线程，  

维护一个state变量， 标识是否锁住， 非0 就是锁住， 还可以表示重入，通过cas机制进行变更值， Semaphore, CountDownLatch， ReentrantLock
维护一个 FIFO的双向链表， 共有两种模式,共享和独占， 还有等待机制
AQS 基于 模板模式， 子类需重写tryrelease, tryacquire, tryReleaseShared, tryAcquireShared方法

### 



