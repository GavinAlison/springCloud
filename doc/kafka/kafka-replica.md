# kafka的replica 副本机制

## 1.1 副本定义

副本就是数据的备份，在主题下的分区下的，分区里面的消息序列的备份就是副本。

在实际生产环境中，每台broker都可能存储不同主题不同分区的不同副本。

一般副本是保存在不同的broker上的，从而能够对抗部分broker宕机带来的数据不可用的后果

## 1.2 副本角色

## 1.3 AR（Assigned Replicas）

## 1.4 OSR（Outof-sync Replicas）

## 1.5 ISR（In-sync Replicas）

## 1.6 Unclean 领导者选举（Unclean Leader Election）

## 1.7 副本Commit

## 1.8 副本处理

## 1.9 副本异常

## 1.10 副本恢复到ISR

## 1.11 kafka 为什么需要多个副本呢
放多份副本来防止数据丢失

## 1.12 kafka 为什么需要zk
因为kafka的Meta数据存储在ZK上的，同时可以利用zk的观察者模式，自动通知观察者做出相应的处理

## 1.13 kafka的元数据储存在哪里？没有zk的时候如何管理集群元数据？

首先要明确Kafka的元数据都指什么。Kafka分为服务器端和客户端。
服务器端的元数据通常是指集群的元数据，包括集群有哪些Broker，有哪些主题，每个主题都有哪些分区，
而每个分区的Leader副本在哪台Broker上等信息。
这些信息保存在ZooKeeper和Controller中。Kafka以ZooKeeper中保存的元数据为权威数据，
Controller会从ZooKeeper中获取最新的元数据并缓存在自己的内存中。

客户端的元数据通常是指消费者的注册信息和位移信息。
在Kafka 0.9版本之前，这些信息的确保存在ZooKeeper中。不过目前已经废弃了。
新版本的消费者把这些信息保存在一个Kafka的内部主题中，由集群中一个名为Coordinator的组件进行管理。

当前，Kafka（主要是指服务器端）是依赖ZooKeeper才能正常运转的。社区后面会移除对ZooKeeper的依赖。
具体的原理是在Kafka内部实现一个基于Raft的Controller集群，来替代ZooKeeper保存和管理集群元数据。


## 1.14 数据可见性

需要注意的是，并不是所有保存在分区首领上的数据都可以被客户端读取到，为了保证数据一致性，
只有被所有同步副本 (ISR 中所有副本) 都保存了的数据才能被客户端读取到。

## 1.15 零拷贝
Kafka 所有数据的写入和读取都是通过零拷贝来实现的。传统拷贝与零拷贝的区别如下：

传统模式下的四次拷贝与四次上下文切换
以将磁盘文件通过网络发送为例。传统模式下，一般使用如下伪代码所示的方法先将文件数据读入内存，然后通过 Socket 将内存中的数据发送出去。

buffer = File.read
Socket.send(buffer)

这一过程实际上发生了四次数据拷贝。首先通过
系统调用将文件数据读入到内核态 Buffer（DMA 拷贝），
然后应用程序将内存态 Buffer 数据读入到用户态 Buffer（CPU 拷贝），
接着用户程序通过 Socket 发送数据时将用户态 Buffer 数据拷贝到内核态 Buffer（CPU 拷贝），
最后通过 DMA 拷贝将数据拷贝到 NIC Buffer。

同时，还伴随着四次上下文切换， 

sendfile和transferTo实现零拷贝

Linux 2.4+ 内核通过 sendfile 系统调用，提供了零拷贝。数据通过 DMA 拷贝到内核态 Buffer 后，直接通过 DMA 拷贝到 NIC Buffer，无需 CPU 拷贝。这也是零拷贝这一说法的来源。除了减少数据拷贝外，因为整个读文件到网络发送由一个 sendfile 调用完成，整个过程只有两次上下文切换，因此大大提高了性能。

从具体实现来看，Kafka 的数据传输通过 TransportLayer 来完成，其子类 PlaintextTransportLayer 的 transferFrom 方法通过调用 Java NIO 中 FileChannel 的 transferTo 方法实现零拷贝，

transferTo 和 transferFrom 并不保证一定能使用零拷贝。实际上是否能使用零拷贝与操作系统相关，如果操作系统提供 sendfile 这样的零拷贝系统调用，则这两个方法会通过这样的系统调用充分利用零拷贝的优势，否则并不能通过这两个方法本身实现零拷贝。




