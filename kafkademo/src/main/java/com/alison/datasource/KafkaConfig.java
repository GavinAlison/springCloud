//package com.alison.config;
//
//import org.apache.kafka.clients.consumer.ConsumerConfig;
//import org.apache.kafka.clients.producer.ProducerConfig;
//import org.apache.kafka.common.serialization.StringDeserializer;
//import org.apache.kafka.common.serialization.StringSerializer;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.annotation.EnableKafka;
//import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
//import org.springframework.kafka.core.*;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import static org.apache.kafka.clients.producer.ProducerConfig.*;
//
//// @EnableKafka注解向容器中导入了 KafkaBootstrapConfiguration 配置类，
//// 此配置类中注入了 KafkaListenerAnnotationBeanPostProcessor , @KafkaListener 注解的后置处理器
////@KafkaListener注解由 KafkaListenerAnnotationBeanPostProcessor 类解析，其实现了BeanPostProcessor接口，
//// 并在postProcessAfterInitialization方法内解析@KafkaListener注解。
//@Configuration
//@EnableKafka
//public class KafkaConfig {
//
//    private String bootstrapServers = "11.11.1.108:9092";
//
//    @Bean
//    ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
//        ConcurrentKafkaListenerContainerFactory<String, String> factory =
//                new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(consumerFactory());
//        return factory;
//    }
//
//    @Bean
//    public ConsumerFactory<String, String> consumerFactory() {
//        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
//    }
//
//    @Bean
//    public Map<String, Object> consumerConfigs() {
//        Map<String, Object> props = new HashMap<>();
//        // kafka集群地址
//        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
//        // groupId
//        props.put(ConsumerConfig.GROUP_ID_CONFIG, "test");
//        props.put(ConsumerConfig.CLIENT_ID_CONFIG, "test-consumer");
//        // session超时设置，14秒，超过这个时间会认为此消费者挂掉，将其从消费组中移除
//        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "14000");
//        // 开启自动提交
//        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
//        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest"); // earliest从最早的offset开始拉取，latest:从最近的offset开始消费
//        //键的反序列化方式，key表示分区
//        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        //值的反序列化方式，key表示分区
//        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        return props;
//    }
//
////    @Bean
////    public Listener listener() {
////        return new Listener();
////    }
//
//    @Bean
//    @ConditionalOnMissingBean(ProducerFactory.class)
//    public ProducerFactory<Integer, String> producerFactory() {
//        return new DefaultKafkaProducerFactory<>(producerConfigs());
//    }
//
//    @Bean
//    public Map<String, Object> producerConfigs() {
//        Map<String, Object> props = new HashMap<>();
//        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
//        props.put(ACKS_CONFIG, "all");
//        props.put(RETRIES_CONFIG, 3); // 重试次数
//        props.put(BATCH_SIZE_CONFIG, 16384); // 批量发送大小
//        props.put(BUFFER_MEMORY_CONFIG, 33554432); // 缓存大小，根据本机内存大小配置
//        props.put(LINGER_MS_CONFIG, 1000); // 发送频率，满足任务一个条件发送
//        props.put(CLIENT_ID_CONFIG, "producer-syn-2"); // 发送端id,便于统计
//        // 重试间隔时间
//        props.put(ProducerConfig.RETRY_BACKOFF_MS_CONFIG, "1000");
//        //键的反序列化方式,key表示分区
//        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//        //值的反序列化方式
//        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//        return props;
//    }
//
//    @Bean
//    public KafkaTemplate<Integer, String> kafkaTemplate() {
//        return new KafkaTemplate<Integer, String>(producerFactory());
//    }
//
//}