# 问题
1.  什么是Disruptor
2.  Disruptor的核心概念
3.  Disruptor是如何消费的
4.  disruptor为什么这么快
5.  disruptor使用场景
6.  如何实现并行消费的额，底层原理是什么
7.  如何简单的使用，1p2c, 1p3c, 3p1c, 3p3c, 如何保证线程安全
8.  什么是伪共享
9.  如何避免伪共享
10. ringbuffer的本质
11. 多个 Producer 如何协调把数据写入到 ringBuffer
12. ringbuffer 如何根据各 consumer 消费速度告知各 Producer 现在是否能写入数据
13. Consumer 是怎么注册的？
14. SequenceBarrier 是用来控制 consumer 读取进度的
15. Disruptor 为什么快而且线程安全
16. handleEventsWith和handleEventsWithWorkerPool方法的区别

11. 多个 Producer 如何协调把数据写入到 ringBuffer
```

```
12. ringbuffer 如何根据各 consumer 消费速度告知各 Producer 现在是否能写入数据
```

```

15. Disruptor 为什么快而且线程安全
```
1.  有ringbuffer的环形数组的数据结构，相比较于链表，添加删除更简单，耗费内存更小，可以利用 CPU 缓存来预加载
2.  数组对象本身一直存在，避免了大对象的垃圾回收（当然元素本身还是要回收的）
3.  在需要确保线程安全的地方，用 CAS 取代锁
4.  没有为伪共享和非预期的竞争，使用了填充缓存行的方式
```

16. handleEventsWith和handleEventsWithWorkerPool方法的区别
```
相同点：
将多个消费者封装到一起，供框架消费消息。

不同点：
1.  对于某一条消息m，handleEventsWith方法返回的EventHandlerGroup，Group中的每个消费者都会对m进行消费，
各个消费者之间不存在竞争。handleEventsWithWorkerPool方法返回的EventHandlerGroup，
Group的消费者对于同一条消息m不重复消费；也就是，如果c0消费了消息m，则c1不再消费消息m。

2.  传入的形参不同。对于独立消费的消费者，应当实现EventHandler接口。
对于不重复消费的消费者，应当实现WorkHandler接口。
```

17. 什么是缓存行，什么是伪共享
```
CPU加载内存数据的最小单元，不是一个字节一个字节加载，是一个缓存行加载64字节，8个long类型变量，一个long存储8字节

线程thread1,thread2同时读取缓存行，线程thread1对缓存行中一个数x进行修改，线程2需要重新读取缓存行，然后对缓存行中数据y修改，
这种重新从主存中读取，表示伪共享

解决伪共享，是通过增大数组元素的间隔，使得不同线程对各自元素的存取操作在不同的缓存行上，就是用空间换时间
```
