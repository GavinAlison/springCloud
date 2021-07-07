# kafka send

```
kafka-console-producer.sh --broker-list 11.11.1.108:9092 --topic mykafka
```

# kafka receive

```
kafka-console-consumer.sh --bootstrap-server 11.11.1.108:9092 --topic mykafka --from-beginning
```


### 问题1： earliest与latest的区别？
-   earliest ：如果一个消费者之前提交过offset。 假设这个消费者中途断过，那当它恢复之后重新连接到队列集群 此时应该是从 它在集群中之前提交的offset点开始继续消费，而不是从头消费。 而一个消费者如果之前没有offset记录并设置earliest ，此时才会从头消费。
-   latest ：当集群中存在消费者之前提交的offset记录时 队列集群会从之前记录的offset点开始发送 [记录的offset点，+无穷) 。而当消费者之前在集群中不存在offset记录点时 会从队列中最新的记录开始消费。


### 问题2：kafka底层的存储机制，选举机制，备份机制

### 问题3： kafka的性能测试

写入性能： 100MB/S
读取性能： 100MB/S

### 问题4： kafka 为什么这么快

### 问题5： Kafka是如何保障数据不丢失的？
         
### 如何解决Kafka数据丢失问题？

### Kafka可以保障永久不丢失数据吗？

### 如何保障Kafka中的消息是有序的？

### 如何确定Kafka主题的分区数量？

### 如何调整生产环境中Kafka主题的分区数量？
    
### 如何重平衡Kafka集群？

### 如何查看消费者组是否存在滞后消费？


