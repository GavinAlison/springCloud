package com.alison.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.*;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.util.Pair;

import java.io.File;
import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static org.apache.kafka.clients.producer.ProducerConfig.*;

@Slf4j
public class KafkaUtils {
    public static void main(String[] args) throws Exception {
        KafkaUtils kafkaDemo = new KafkaUtils();
        kafkaDemo.send();
        kafkaDemo.consumer();
    }

    private KafkaConsumer<String, String> kafkaConsumer;
    private KafkaProducer<String, String> kafkaProducer;

    @Before
    public void comsumerConfig() {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "11.11.1.108:9092");
        properties.put("group.id", "test1");
        properties.put("auto.offset.reset", "earliest");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        kafkaConsumer = new KafkaConsumer<>(properties);
    }

    @Before
    public void producerConfig() {
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
        kafkaProducer = new KafkaProducer<>(properties);
    }

    private Properties adminProperties;

    @Before
    public void adminConfig() {
        adminProperties = new Properties();
        adminProperties.put("bootstrap.servers", "11.11.1.108:9092");
        adminProperties.put("connections.max.idle.ms", 10000);
        adminProperties.put("request.timeout.ms", 5000);
    }

    @Test
    public void consumer() {
        consumerByTopic("myKafka");
    }

    public void consumerByTopic(String topic) {
        kafkaConsumer.subscribe(Arrays.asList(topic));
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
        kafkaProducer.initTransactions();
        kafkaProducer.beginTransaction();
        kafkaProducer.send(new ProducerRecord<>("mykafka", json));
        kafkaProducer.commitTransaction();
    }

    @Test
    public void send() throws Exception {
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
    public void sendByUsage() throws Exception {
        Pair<String, String> one = Pair.of("unBoundedJoin01_t1", "543462,1001,1511658000");
        Pair<String, String> two = Pair.of("unBoundedJoin01_t2", "1001,4238,1511658001");
        kafkaProducer.initTransactions();
        kafkaProducer.beginTransaction();
        kafkaProducer.send(new ProducerRecord<>(one.getFirst(), one.getSecond()));
        kafkaProducer.send(new ProducerRecord<>(two.getFirst(), two.getSecond()));
        kafkaProducer.commitTransaction();
        TimeUnit.SECONDS.sleep(10);
    }

    @Test
    public void adminDelete() {
        try (AdminClient client = AdminClient.create(adminProperties)) {
            client.deleteTopics(Arrays.asList("unBoundedJoin01_t1", "unBoundedJoin01_t2")).all().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private String topic = "unBoundedJoin01_t2";

    @Test
    public void adminCreateTopicByUsage() {
        try (AdminClient client = AdminClient.create(adminProperties)) {
            CreateTopicsResult result = client.createTopics(Arrays.asList(new NewTopic(topic, 1, (short) 1)));
            result.all().get();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void adminCreateTopic() {
        try (AdminClient client = AdminClient.create(adminProperties)) {
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
        try (AdminClient client = AdminClient.create(adminProperties)) {
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
        try (AdminClient client = AdminClient.create(adminProperties)) {
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
