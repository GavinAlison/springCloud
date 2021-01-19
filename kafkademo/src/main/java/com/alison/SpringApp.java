package com.alison;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

@SpringBootApplication
public class SpringApp {

    public static Logger logger = LoggerFactory.getLogger(SpringApp.class);

    public static void main(String[] args) {
        SpringApplication.run(SpringApp.class, args);
    }

//    // 消费消息可以通过配置一个MessageListenerContainer然后提供一个Message Listener去接收消息；或者使用@KafkaListener注解
    @KafkaListener(topics = "connect-test")
    public void listen(ConsumerRecord<String, String> cr) throws Exception {
        logger.info("topic: {}, key: {}, value: {}", cr.topic(), cr.key(), cr.value());
    }

}
