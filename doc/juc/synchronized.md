# synchronized 

这个是jvm语义级别的，是在对象头的mark word中表示

底层，synchronized的流转过程，无锁--》偏向锁--》 轻量级锁--》重量锁

这些锁的标识是通过mark word中是否偏向锁+锁标识字段进行控制的

## 为什么 wait 方法定义在 Object 类里面，而不是 Thread 类
>https://blog.csdn.net/github_38592071/article/details/107438211
1.  wait 和 notify 不仅仅是普通方法或同步工具，更重要的是它们是 Java 中两个线程之间的通信机制。
对语言设计者而言, 如果不能通过 Java 关键字(例如 synchronized)实现通信此机制，
同时又要确保这个机制对每个对象可用, 那么 Object 类则是的合理的声明位置。
记住同步和等待通知是两个不同的领域，不要把它们看成是相同的或相关的。
同步是提供互斥并确保 Java 类的线程安全，而 wait 和 notify 是两个线程之间的通信机制。

2.  每个对象都可上锁，这是在 Object 类而不是 Thread 类中声明 wait 和 notify 的另一个原因。

3.  在 Java 中，为了进入代码的临界区，线程需要锁定并等待锁，他们不知道哪些线程持有锁，而只是知道锁被某个线程持有， 并且需要等待以取得锁, 而不是去了解哪个线程在同步块内，并请求它们释放锁。

4.  Java 是基于 Hoare 的监视器的思想(http://en.wikipedia.org/wiki/...)。在Java中，所有对象都有一个监视器。




>link: https://www.jianshu.com/p/8dd5a54237dd
>https://zhuanlan.zhihu.com/p/151856103
>https://www.jianshu.com/p/6d62c3ee48d0
>https://www.zhihu.com/topic/19566470/hot
>