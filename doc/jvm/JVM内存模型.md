1.  [JVM内存模型](https://zhuanlan.zhihu.com/p/101495810)
2.  [JVM堆栈内存分析](https://blog.csdn.net/mocas_wang/article/details/108450605)
3.  [Sun JVM 内存管理、参数与调优、内存分配与回收策略](https://blog.csdn.net/wangmx1993328/article/details/88934706)
4.  [jvm入门，什么是JVM？多图详解](https://blog.csdn.net/mocas_wang/article/details/107876197)
5.  [深入了解JVM内存模型](https://blog.csdn.net/mocas_wang/article/details/107936432)



目录：
1.  程序计数器 program counter register
2.  虚拟机栈 vm stack
3.  本地方法栈 native method stack
4.  堆heap
5.  方法区method area
虚拟机加载的类信息
6.  JVM 栈堆分析
7.  实例分析
8.  jvm 参数
```
-server 优化编译，提高性能
-Xss    单个线程栈大小，默认1M, 相同内存下，减少这个值能有更多线程。
操作系统对一个线程数有限制，在3000-5000左右
-XX:+UseParNewGC    可用来设置年轻代为并发收集【多CPU】，
如果你的服务器有多个CPU，你可以开启此参数；开启此参数，
多个CPU可并发进行垃圾回收，可提高垃圾回收的速度。此参数
和+UseParallelGC，-XX:ParallelGCThreads搭配使用。
-XX:+UseParallelGC  选择垃圾收集器为并行收集器。此配置仅对年轻代有效。
即上述配置下，年轻代使用并发收集，而年老代仍旧使用串行收集 。
可提高系统的吞吐量
-XX:ParallelGCThreads	年轻代并行垃圾收集的前提下（对并发也有效果）的线程数，
增加并行度，即：同时多少个线程一起进行垃圾回收。此值最好配置与处理器数目相等。
```
9.  永久存储区相关参数
```
-Xnoclassgc	每次永久存储区满了后一般GC算法在做扩展分配内存前都会触发一次FULL GC，
除非设置了-Xnoclassgc.
-XX:PermSize	应用服务器启动时，永久存储区的初始内存大小
-XX:MaxPermSize	应用运行中，永久存储区的极限值。为了不消耗扩大 JVM 
永久存储区分配的开销，将此参数和-XX:PermSize 这个两个值设为相等。
默认64M
```
10. 堆空间相关参数
```
-Xms	启动应用时，JVM 堆空间的初始大小值。
-Xmx	应用运行中，JVM 堆空间的极限值。为了不消耗扩大JVM堆空间分配的开销，
将此参数和-Xms这个两个值设为相等，考虑到需要开线程，将此值设置为总内存的80%.
-Xmn	此参数硬性规定堆空间的新生代空间大小，推荐设为堆空间大小的1/4。
```

11. OOM种类
```
永久存储区溢出（java.lang.OutOfMemoryError: Java Permanent Space）"，
"JVM堆空间溢出（java.lang.OutOfMemoryError: Java heap space）"这两大溢出错误。
永久存储区溢出是永久存储区设置太小，不能满足系统需要的大小，此时只需要调整 
-XX:PermSize 和 -XX:MaxPermSize 这两个参数即可。JVM堆空间溢出是JVM堆空间不足，
此时只需要调整 -Xms 和 -Xmx 这两个参数即可。
```
12. 内存分配与回收策略
```
1、对象的内存分配，主要分配在新生代的 Eden 区上，少数情况下也可能直接分配在老年代中。
2、优先在 Eden 区分配：大多数情况下，对象在新生代 Eden 区分配，当 Eden 区空间不够时，发起 Minor GC。
3、大对象直接进入老年代
1）大对象是指需要连续内存空间的对象，最典型的大对象是那种很长的字符串以及数组。经常出现大对象会提前触发垃圾收集以获取足够的连续空间分配给大对象。
2）提供 -XX:PretenureSizeThreshold 参数，大于此值的对象直接在老年代分配，避免在 Eden 区和 Survivor 区之间的大量内存复制。

4、长期存活的对象进入老年代
JVM 为对象定义年龄计数器，经过 Minor GC 依然存活，并且能被 Survivor 区容纳的，
移被移到 Survivor 区，年龄就增加 1 岁，增加到一定年龄则移动到老年代中（默认 15 岁，
通过 -XX:MaxTenuringThreshold 设置）。
```

13. full gc 触发条件
1. mini gc 触发，eden满了就触发
2. full gc 触发
1). 手动， 不建议，System.gc()。，但很多情况下它会触发 Full GC，从而增加 Full GC 的频率，也即增加了间歇性停顿的次数。
2). 老年代空间不足。    老年代空间不足的常见场景为前文所讲的大对象直接进入老年代、长期存活的对象进入老年代等。
3). 空间分配担保失败。

14. 永久代存储对象主要包括以下几类：
```
1、加载/缓存到内存中的 class 定义，包括类的名称，字段，方法和字节码；
2、常量池；
3、对象数组/类型数组所关联的 class；
4、JIT 编译器优化后的 class 信息。
```


15. hotspot对象布局
1)  mark word
2)  实例数据
3)  对其填充

16. 对象访问
1)  句柄访问
2)  直接指针

哪些对象会作为GC Root被回收

