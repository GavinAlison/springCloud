#CyclicBarrier

CyclicBarrier叫做回环屏障，它的作用是让一组线程全部达到一个状态之后再全部同时执行，而且他有一个特点就是所有线程执行完毕之后是可以重用的。

至此我们难免会将CyclicBarrier与CountDownLatch进行一番比较。
 * 这两个类都可以实现一组线程在到达某个条件之前进行等待，它们内部都有一个计数器，当计数器的值不断的减为0的时候所有阻塞的线程将会被唤醒。
 * 有区别的是CyclicBarrier的计数器由自己控制，而CountDownLatch的计数器则由使用者来控制，
 * 在CyclicBarrier中线程调用await方法不仅会将自己阻塞还会将计数器减1，而在CountDownLatch中线程调用await方法只是将自己阻塞而不会减少计数器的值。
 *
 * 另外，CountDownLatch只能拦截一轮，而CyclicBarrier可以实现循环拦截。
 * 一般来说用CyclicBarrier可以实现CountDownLatch的功能，而反之则不能，
 * 例如上面的赛马程序就只能使用CyclicBarrier来实现。总之，这两个类的异同点大致如此，
 * 至于何时使用CyclicBarrier，何时使用CountDownLatch，还需要读者自己去拿捏。
 *
 * 除此之外，CyclicBarrier还提供了：resert()、getNumberWaiting()、isBroken()等比较有用的方法。
 
 
CyclicBarrier 还是基于AQS实现的，内部维护parties记录总线程数，count用于计数，最开始count=parties，调用await()之后count原子递减，当count为0之后，再次将parties赋值给count，这就是复用的原理。

当子线程调用await()方法时，获取独占锁，同时对count递减，进入阻塞队列，然后释放锁
当第一个线程被阻塞同时释放锁之后，其他子线程竞争获取锁，操作同1
直到最后count为0，执行CyclicBarrier构造函数中的任务，执行完毕之后子线程继续向下执行