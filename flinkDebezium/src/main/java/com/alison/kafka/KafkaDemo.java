package com.alison.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.*;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.Test;

import java.io.File;
import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static org.apache.kafka.clients.producer.ProducerConfig.*;

@Slf4j
public class KafkaDemo {
    public static void main(String[] args) throws Exception {
        KafkaDemo kafkaDemo = new KafkaDemo();
        kafkaDemo.send();
        kafkaDemo.consumer();
    }

    public void consumer() {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "11.11.1.108:9092");
        properties.put("group.id", "test1");
        properties.put("auto.offset.reset", "earliest");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(properties);
        kafkaConsumer.subscribe(Arrays.asList("mykafka"));
        while (true) {
            ConsumerRecords<String, String> records = kafkaConsumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                log.info("topic=>{}, offset=>{}, partition=>{}, key=> {}, value=> {}",
                        record.topic(), record.offset(), record.partition(),
                        record.key(), record.value());
            }
        }
    }

    @Test
    public void sendAppend() {
        String json = "{\"user_id\": \"543466\", \"item_id\":\"1718\", \"category_id\": \"1464116\", \"behavior\": \"pv\", \"ts\": \"2017-11-29 01:00:00\"}";
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "11.11.1.108:9092");
        properties.put("group.id", "test1");
        properties.put("auto.offset.reset", "earliest");
        properties.put(ACKS_CONFIG, "all");
        properties.put(RETRIES_CONFIG, 3); // 重试次数
        properties.put(BATCH_SIZE_CONFIG, 1000); // 批量发送大小
        properties.put(BUFFER_MEMORY_CONFIG, 33554432); // 缓存大小，根据本机内存大小配置
        properties.put(LINGER_MS_CONFIG, 1000); // 发送频率，满足任务一个条件发送
        properties.put(CLIENT_ID_CONFIG, "producer-syn-2"); // 发送端id,便于统计
        properties.put(KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        properties.put(VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        properties.put(TRANSACTIONAL_ID_CONFIG, "producer-1"); // 每台机器唯一
        properties.put(ENABLE_IDEMPOTENCE_CONFIG, true); // 设置幂等性

        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(properties);
        kafkaProducer.initTransactions();
        kafkaProducer.beginTransaction();
        kafkaProducer.send(new ProducerRecord<>("mykafka", json));
        kafkaProducer.commitTransaction();
    }

    @Test
    public void send() throws Exception {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "11.11.1.108:9092");
        properties.put("group.id", "test1");
        properties.put("auto.offset.reset", "earliest");
        properties.put(ACKS_CONFIG, "all");
        properties.put(RETRIES_CONFIG, 3); // 重试次数
        properties.put(BATCH_SIZE_CONFIG, 1000); // 批量发送大小
        properties.put(BUFFER_MEMORY_CONFIG, 33554432); // 缓存大小，根据本机内存大小配置
        properties.put(LINGER_MS_CONFIG, 1000); // 发送频率，满足任务一个条件发送
        properties.put(CLIENT_ID_CONFIG, "producer-syn-2"); // 发送端id,便于统计
        properties.put(KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        properties.put(VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        properties.put(TRANSACTIONAL_ID_CONFIG, "producer-1"); // 每台机器唯一
        properties.put(ENABLE_IDEMPOTENCE_CONFIG, true); // 设置幂等性

        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(properties);
        kafkaProducer.initTransactions();
        kafkaProducer.beginTransaction();
        String path = "D:\\jrxTmp\\spingcloud\\flinkDebezium\\src\\main\\resources\\message.txt";
        List<String> messages = Files.readAllLines(new File(path).toPath());
        messages.forEach(e -> {
            kafkaProducer.send(new ProducerRecord<String, String>("mykafka", e));
        });
        kafkaProducer.commitTransaction();
        TimeUnit.SECONDS.sleep(10);
    }

    @Test
    public void adminDelete() {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "11.11.1.108:9092");
        properties.put("connections.max.idle.ms", 10000);
        properties.put("request.timeout.ms", 5000);
        try (AdminClient client = AdminClient.create(properties)) {
            client.deleteTopics(Arrays.asList("mykafka")).all().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private String topic = "unBoundedJoin01_t2";

    @Test
    public void adminCreateTopicByUsage() {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "11.11.1.108:9092");
        properties.put("connections.max.idle.ms", 10000);
        properties.put("request.timeout.ms", 5000);
        try (AdminClient client = AdminClient.create(properties)) {
            CreateTopicsResult result = client.createTopics(Arrays.asList(new NewTopic(topic, 1, (short) 1)));
            result.all().get();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void adminCreateTopic() {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "11.11.1.108:9092");
        properties.put("connections.max.idle.ms", 10000);
        properties.put("request.timeout.ms", 5000);
        try (AdminClient client = AdminClient.create(properties)) {
            CreateTopicsResult result = client.createTopics(Arrays.asList(
                    new NewTopic("topic1", 1, (short) 1),
                    new NewTopic("topic2", 1, (short) 1),
                    new NewTopic("topic3", 1, (short) 1)
            ));
            try {
                result.all().get();
            } catch (InterruptedException | ExecutionException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    @Test
    public void listTopics() {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "11.11.1.108:9092");
        properties.put("connections.max.idle.ms", 10000);
        properties.put("request.timeout.ms", 5000);
        try (AdminClient client = AdminClient.create(properties)) {
            ListTopicsResult result = client.listTopics();
            try {
                //topic name, topic listings
                System.out.println(result.names().get());
                System.out.println(result.listings().get());
                Map<String, TopicDescription> stringTopicDescriptionMap = client.describeTopics(result.names().get()).all().get();
                stringTopicDescriptionMap.entrySet().forEach(en -> {
                    System.out.println(String.format("key-> %s, value-> %s", en.getKey(), en.getValue().toString()));
                });
            } catch (InterruptedException | ExecutionException e) {
                throw new IllegalStateException(e);
            }
        }

    }

    @Test
    public void addPartition() {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "11.11.1.108:9092");
        properties.put("connections.max.idle.ms", 10000);
        properties.put("request.timeout.ms", 5000);

        try (AdminClient client = AdminClient.create(properties)) {
            Map newPartitions = new HashMap<>();
            // 增加到2个
            newPartitions.put("topic1", NewPartitions.increaseTo(2));
            CreatePartitionsResult rs = client.createPartitions(newPartitions);
            try {
                rs.all().get();
            } catch (InterruptedException | ExecutionException e) {
                throw new IllegalStateException(e);
            }
        }
    }

}
