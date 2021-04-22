package com.alison.stream;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.JoinWindows;
import org.apache.kafka.streams.kstream.Joined;
import org.apache.kafka.streams.kstream.KStream;

import java.time.Duration;
import java.util.Properties;

public class StreamLeftJoin {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "leftjoin-application");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaCOnstant.BOOTSTRAP_SERVERS);
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        StreamsBuilder builder = new StreamsBuilder();
        KStream<String, String> left = builder.stream("topic-03");
        KStream<String, String> right = builder.stream("topic-04");

        KStream<String, String> joined = left.selectKey(((key, value) -> value.split(",")[0]))
                .leftJoin(right.selectKey((key, value) -> value.split(",")[0]),
                        (leftValue, rightValue) -> "left=" + leftValue + ", right=" + rightValue, /* ValueJoiner */
                        JoinWindows.of(Duration.ofMinutes(5)),
                        Joined.with(
                                // key
                                Serdes.String(),
                                // left value
                                Serdes.String(),
                                // right value
                                Serdes.String())
                );

        joined.foreach((key, value) -> System.out.println(key + " => " + value));
        KafkaStreams streams = new KafkaStreams(builder.build(), props);
        streams.start();
    }
}
