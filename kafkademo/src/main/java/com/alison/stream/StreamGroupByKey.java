
package com.alison.stream;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.KGroupedStream;
import org.apache.kafka.streams.kstream.KStream;

import java.util.Properties;

import static com.alison.stream.KafkaCOnstant.BOOTSTRAP_SERVERS;

public class StreamGroupByKey {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "groupbykey-application");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        StreamsBuilder builder = new StreamsBuilder();
        KStream<String, String> kstream = builder.stream("topic-02");
        KGroupedStream<String, String> groupedStream = kstream.groupByKey(
                Grouped.with(Serdes.String(), Serdes.String())
        );

        groupedStream.count().toStream().foreach((key, value) -> System.out.println(key + " => " + value));
        KafkaStreams streams = new KafkaStreams(builder.build(), props);
        streams.start();
    }
}
