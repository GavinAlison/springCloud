# synchronized 

这个是jvm语义级别的，是在对象头的mark word中表示

底层，synchronized的流转过程，无锁--》偏向锁--》 轻量级锁--》重量锁

这些锁的标识是通过mark word中是否偏向锁+锁标识字段进行控制的

mark word 主要是32bit, 25bit 哈希码，4bit 分代年龄， 1bit偏向锁标识，2bit 锁标识位

不同状态存储数据也不一样
|锁状态| 存储内容 | 偏向锁标识位| 锁标识位|
|无锁| 

