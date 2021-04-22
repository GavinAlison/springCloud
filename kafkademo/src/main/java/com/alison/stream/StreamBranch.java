package com.alison.stream;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.ForeachAction;
import org.apache.kafka.streams.kstream.KStream;

import java.util.Properties;

import static com.alison.stream.KafkaCOnstant.BOOTSTRAP_SERVERS;

public class StreamBranch {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "branch-application");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        StreamsBuilder builder = new StreamsBuilder();
        KStream<String, String> kstream = builder.stream("topic-02");
        KStream<String, String>[] branches = kstream.branch(
                // 第一个谓词
                (key, value) -> key.startsWith("keyA"),
                // 第二个谓词
                (key, value) -> key.startsWith("keyB")
        );

        for (KStream<String, String> stream : branches) {
            stream.foreach(new ForeachAction<String, String>() {
                @Override
                public void apply(String key, String value) {
                    System.out.println(key + " => " + value);
                }
            });
        }

        KafkaStreams streams = new KafkaStreams(builder.build(), props);
        streams.start();
    }
}
