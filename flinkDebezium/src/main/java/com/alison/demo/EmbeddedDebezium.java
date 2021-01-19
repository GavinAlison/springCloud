package com.alison.demo;

import io.debezium.engine.ChangeEvent;
import io.debezium.engine.DebeziumEngine;
import io.debezium.engine.format.Json;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class EmbeddedDebezium {

    private static Logger log = LoggerFactory.getLogger(EmbeddedDebezium.class);

    public static void main(String[] args) {
        // Define the configuration for the Debezium Engine with MySQL connector...
        final Properties props = new Properties();
        props.setProperty("name", "engine");
        props.setProperty("offset.storage", "org.apache.kafka.connect.storage.FileOffsetBackingStore");
        props.setProperty("offset.storage.file.filename", "D:\\jrxTmp\\spingcloud\\flinkDebezium\\src\\main\\resources\\offsets.dat");
        props.setProperty("offset.flush.interval.ms", "60");
        props.setProperty("offset.flush.timeout.ms", "0");
        /* begin connector properties */
        props.setProperty("database.hostname", "11.11.1.108");
        props.setProperty("database.port", "3306");
        props.setProperty("database.user", "root");
        props.setProperty("database.password", "123.com");
        props.setProperty("database.server.id", "85744");
        props.setProperty("database.server.id", "85744");
        props.setProperty("database.server.name", "myapp");
        props.setProperty("database.history",
                "io.debezium.relational.history.FileDatabaseHistory");
        props.setProperty("database.history.file.filename",
                "D:\\jrxTmp\\spingcloud\\flinkDebezium\\src\\main\\resources\\dbhistory.dat");
        // Define the configuration for the Debezium Engine with MySQL connector...
        props.setProperty("connector.class", "io.debezium.connector.mysql.MySqlConnector");

// Create the engine with this configuration ...
        try (DebeziumEngine<ChangeEvent<String, String>> engine = DebeziumEngine.create(Json.class)
                .using(props)
                .notifying(record -> {
                    System.out.println(record);
                }).build()
        ) {
            // Run the engine asynchronously ...
            ExecutorService executor = Executors.newSingleThreadExecutor();
            executor.execute(engine);
            TimeUnit.SECONDS.sleep(60);
            while (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                log.info("Waiting another 5 seconds for the embedded engine to shut down");
            }
//            executor.shutdown();
            // Do something else or wait for a signal or an event
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
// Engine is stopped when the main code is finished

    }
}
