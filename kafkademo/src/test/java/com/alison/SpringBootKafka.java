package com.alison;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;

// 自动装配在@EnableKafka存在的情况下加载配置信息KafkaAutoConfiguration, KafkaProperties
@EnableKafka
@SpringBootTest
public class SpringBootKafka implements ApplicationRunner {

    public static Logger logger = LoggerFactory.getLogger(SpringApp.class);

    public static void main(String[] args) {
        SpringApplication.run(SpringApp.class, args);
    }

    @Autowired
    private KafkaTemplate<String, String> template;

//    private final CountDownLatch latch = new CountDownLatch(3);

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        this.template.send("topic", "foo1");
//        this.template.send("topic", "foo2");
//        this.template.send("topic", "foo3");
//        latch.await(60, TimeUnit.SECONDS);
//        logger.info("All received");
    }

    //    消费消息可以通过配置一个MessageListenerContainer然后提供一个Message Listener去接收消息；或者使用@KafkaListener注解
    @KafkaListener(topics = {"topic", "connect-test"})
    public void listen(ConsumerRecord<String, String> record) throws Exception {
        logger.info("topic: {}, key: {}, value: {}", record.topic(), record.key(), record.value());
//        latch.countDown();
    }

}
