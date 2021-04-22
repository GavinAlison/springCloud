# CountDownLatch
适用于多线程的场景需要等待所有子线程全部执行完毕之后正在做操作的场景

```
new CountDownLatch(num);
countDownlatch.countDown()
countDownLatch.await()
```


CountDownLatch 就是一个计数器，内部保存一个数值，作用可以让当前线程等待计数器为0，才获取
方法await、countDown,
内部使用Sync内部类进行处理计数器的原子操作
Sync 继承 AbstractQueuedSynchronizer

