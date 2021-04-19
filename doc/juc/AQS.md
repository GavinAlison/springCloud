# AQS
其实AQS 是包含两个队列和一个变量的state，       
哪两个队列， 同步队列(等待队列) 和 条件队列        
state变量，就是用于状态变量，可以用于锁的重入，线程的限流，计数

AQS的全称： AbstractQueuedSynchronizer，抽象的队列同步器

同步队列: 双向链表
条件队列：单向链表

## AQS的数据结构

conditionObject
Node
state

Node head
Node tail

## AQS的method


acquire(int)        
release(int)        
getState()      

## AQS的场景


## AQS的子类


## Node
```
thread
waitStatus

pre
next
nextWaiter
```



