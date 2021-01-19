package com.alison;

import ch.qos.logback.classic.Level;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.Test;
import org.slf4j.impl.StaticLoggerBinder;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.Acknowledgment;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
public class KafkaTest {

//    @Autowired
//    private KafkaTemplate kafkaTemplate;

    /**
     * link : https://blog.csdn.net/weixin_37598682/article/details/103622886
     * 用KafkaTemplate 同步发送消息
     * 消息结果回调
     * 在发送消息完之后休眠了1秒,采取异步方式发送
     * Kafka的事务管理
     * （一）配置Kafka事务管理器并使用@Transactional注解
     * （二）使用KafkaTemplate的executeInTransaction方法
     * kafka的消息监听器
     * 1.单数据消费的MessageListener、
     * 2.批量消费的BatchMessageListener、
     * 3.具备ACK机制的AcknowledgingMessageListener和BatchAcknowledgingMessageListener
     * @throws Exception
     */
    @Test
    public void testproducer() throws Exception {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.RETRIES_CONFIG, 3);// # 重试次数
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);// # 批量发送的消息数量
        props.put(ProducerConfig.LINGER_MS_CONFIG, 1);
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);//  # 32MB的批处理缓冲区
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//        props.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, "producer-txid-1");

        ProducerFactory<String, String> producerFactory = new DefaultKafkaProducerFactory(props);
        KafkaTemplate kafkaTemplate = new KafkaTemplate(producerFactory);

        kafkaTemplate.send("topic", "data1");
        // 在发送消息完之后休眠了1秒，否则发送时间较长的时候会导致进程提前关闭导致无法调用回调时间。主要是因为KafkaTemplate发送消息是采取异步方式发送的
        Thread.sleep(1);
        log.info("topic: {} value: {}", "topic", "data1");

        ///--------------2
//        KafkaTransactionManager kafkaTransactionManager = new KafkaTransactionManager(producerFactory);
//        kafkaTemplate.executeInTransaction(new KafkaOperations.OperationsCallback() {
//
//            @Override
//            public Object doInOperations(KafkaOperations operations) {
//                operations.send("topic", "data1");
//                return true;
//            }
//        });

    }

    @KafkaListener(topics = "topic")
    public void listen(ConsumerRecord<?, ?> record, Acknowledgment acknowledgment) {
        log.info("test-key={}", record.key());
        log.info("test-value={}", record.value());
        log.info("test-topic={}", record.topic());
        // 手动提交offset
        acknowledgment.acknowledge();
    }

    @Test
    public void testConsumer() throws Exception {

        ch.qos.logback.classic.Logger logger = (ch.qos.logback.classic.Logger) StaticLoggerBinder.getSingleton().getLoggerFactory().getLogger("org.apache.kafka");
        logger.setLevel(Level.OFF);

        Map<String, Object> map = Maps.newHashMap();
        map.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        map.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"); // latest
        map.put(ConsumerConfig.GROUP_ID_CONFIG, "dml");
        map.put(ConsumerConfig.CLIENT_ID_CONFIG, "consumer-1");
//        map.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 120000);
//        map.put(ConsumerConfig.REQUEST_TIMEOUT_MS_CONFIG, 180000);
        map.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        map.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        Consumer<String, String> consumer = new KafkaConsumer(map);
        consumer.subscribe(Arrays.asList("topic"));
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(20));
            if (!records.isEmpty()) {
                Iterator<ConsumerRecord<String, String>> iterator = records.iterator();
                while (iterator.hasNext()) {
                    ConsumerRecord<String, String> next = iterator.next();
                    log.debug("消费到一条kafka数据 topic:{},partition:{},key:{},value:{}", next.topic(), next.partition(), next.key(), next.value());
                }
            }
            TimeUnit.SECONDS.sleep(1);
        }
    }
}
