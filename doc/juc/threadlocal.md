# ThreadLocalMap里弱引用

每个Thread内部都维护一个ThreadLocalMap字典数据结构，
字典的Key值是ThreadLocal，那么当某个ThreadLocal对象不再使用
（没有其它地方再引用）时，每个已经关联了此ThreadLocal的线程
怎么在其内部的ThreadLocalMap里做清除此资源呢？
JDK中的ThreadLocalMap又做了一次精彩的表演，
它没有继承java.util.Map类，而是自己实现了一套专门用来定时清理无效资源的字典结构。
其内部存储实体结构Entry<ThreadLocal, T>继承自java.lan.ref.WeakReference，
这样当ThreadLocal不再被引用时，因为弱引用机制原因，当jvm发现内存不足时，
会自动回收弱引用指向的实例内存，即其线程内部的ThreadLocalMap会释放其对ThreadLocal的引用
从而让jvm回收ThreadLocal对象。这里是重点强调下，是回收对ThreadLocal对象，
而非整个Entry，所以线程变量中的值T对象还是在内存中存在的，
所以内存泄漏的问题还没有完全解决。接着分析JDK的实现，
会发现在调用ThreadLocal.get()或者ThreadLocal.set(T)时都会定期执行回收无效的Entry操作。

# ThreadLocal的内存溢出情况

ThreadLocalMap存储的格式是Entry<ThreadLocal, T>。

Entry中的key是弱引用，key 弱指向ThreadLocal<UserInfo> 对象，并且Key只是userInfoLocal强引用的副本（结合第一个问题），value是userInfo对象。

当我显示的把userInfoLocal = null 时就只剩下了key这一个弱引用，GC时也就会回收掉ThreadLocal<UserInfo> 对象。

但是我们最好避免threadLocal=null的操作，尽量用threadLocal.remove()来清除。因为前者中的userInfo对象还是存在强引用在当前线程中，只有当前thread结束以后, current thread就不会存在栈中,强引用断开, 会被GC回收。但是如果用的是线程池，那么的话线程就不会结束，只会放在线程池中等待下一个任务，但是这个线程的 map 还是没有被回收，它里面存在value的强引用，所以会导致内存溢出。


# 弱引用 WeakReference

如果一个对象只具有弱引用，那么垃圾回收器在扫描到该对象时，无论内存充足与否，都会回收该对象的内存。



> link: https://blog.csdn.net/vicoqi/article/details/79743112
