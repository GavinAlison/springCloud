# kafka send

```text
kafka-console-producer.sh --broker-list 11.11.1.108:9092 --topic mykafka
```

# kafka receive

```text
kafka-console-consumer.sh --bootstrap-server 11.11.1.108:9092 --topic mykafka --from-beginning
```


### 问题1： earliest与latest的区别？
-   earliest ：如果一个消费者之前提交过offset。 假设这个消费者中途断过，那当它恢复之后重新连接到队列集群 此时应该是从 它在集群中之前提交的offset点开始继续消费，而不是从头消费。 而一个消费者如果之前没有offset记录并设置earliest ，此时才会从头消费。
-   latest ：当集群中存在消费者之前提交的offset记录时 队列集群会从之前记录的offset点开始发送 [记录的offset点，+无穷) 。而当消费者之前在集群中不存在offset记录点时 会从队列中最新的记录开始消费。




