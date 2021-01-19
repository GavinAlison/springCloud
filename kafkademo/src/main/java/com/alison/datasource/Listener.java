//package com.alison.config;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.kafka.annotation.KafkaListener;
//
//import java.util.concurrent.CountDownLatch;
//
//@Slf4j
//public class Listener {
//
//    public static CountDownLatch latch1 = new CountDownLatch(1);
//
//    @KafkaListener(id = "foo", topics = "topic1")
//    public void listen1(String foo) {
//        log.info("foo===>{} ", foo);
//        this.latch1.countDown();
//    }
//
//}