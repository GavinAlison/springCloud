package com.alison.stream;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;

import java.util.Properties;

import static com.alison.stream.KafkaCOnstant.BOOTSTRAP_SERVERS;

public class StreamGroupby {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "groupby-application");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        StreamsBuilder builder = new StreamsBuilder();
        KStream<String, String> kstream = builder.stream("topic-02");
        KTable<String, String> table = kstream.toTable();

        // 按新Key和其类型对流进行分组
        KGroupedStream<String, String> groupedStream = kstream.groupBy(
                new KeyValueMapper<String, String, String>() {
                    @Override
                    public String apply(String key, String value) {
                        return value;
                    }
                },
                Grouped.with(
                        // key 可以被修改
                        Serdes.String(),
                        Serdes.String())
        );

        // 按新的Key和其类型对表进行分组，同时修改值和值类型
        KGroupedTable<String, String> groupedTable = table.groupBy(
                new KeyValueMapper<String, String, KeyValue<String, String>>() {
                    @Override
                    public KeyValue<String, String> apply(String key, String value) {
                        return KeyValue.pair(key, key + "," + value);
                    }
                },
                Grouped.with(
                        // 类型可以被修改
                        Serdes.String(),
                        Serdes.String())
        );

        KafkaStreams streams = new KafkaStreams(builder.build(), props);
        streams.start();
    }
}
