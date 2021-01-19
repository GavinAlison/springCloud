//package com.alison.config;
//
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Component;
//
//import java.util.concurrent.TimeUnit;
//
//import static org.junit.Assert.assertTrue;
//
//@Component
//public class KafkaConfigurationTest {
//
//    @Autowired
//    private Listener listener;
//
//    @Autowired
//    private KafkaTemplate<Integer, String> template;
//
//    @Test
//    public void testSimple() throws Exception {
//        template.send("annotated1", 0, "foo");
//        template.flush();
//        assertTrue(this.listener.latch1.await(10, TimeUnit.SECONDS));
//    }
//}
