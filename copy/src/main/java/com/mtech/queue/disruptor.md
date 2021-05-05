# 问题
1.  什么是Disruptor
2.  Disruptor的核心概念
3.  API
4.  为什么这么设计,跟blockingQueue的区别在哪儿,除了这种设计还有其他设计嘛,
跟MQ的对比

1. 什么是Disruptor
就是一个无锁队列

2. Disruptor的核心概念

Ring Buffer 环形的缓冲区      
Sequence  Disruptor 通过顺序递增的序号来编号管理通过其进行交换的数据（事件）        
Sequencer   。此接口有两个实现类 SingleProducerSequencer、MultiProducerSequencer 
Sequence Barrier    用于保持对RingBuffer的 main published Sequence 和Consumer依赖的   
其它Consumer的 Sequence 的引用
Wait Strategy   定义 Consumer 如何进行等待下一个事件的策略  
Event   在 Disruptor 的语义中，生产者和消费者之间进行交换的数据被称为事件(Event)   
EventProcessor  EventProcessor 持有特定消费者(Consumer)的 Sequence，并提供用于调用事件处理实现的事件循环(Event Loop)。  
EventHandler    Disruptor 定义的事件处理接口，由用户实现，用于处理事件，是 Consumer 的真正实现。  
Producer    即生产者，只是泛指调用 Disruptor 发布事件的用户代码，Disruptor 没有定义特定接口或类型。  


## link:
>https://zhuanlan.zhihu.com/p/148441085
>https://www.cnblogs.com/haiq/p/4112689.html


