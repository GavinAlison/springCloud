package com.alison.demo;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.SqlDialect;
import org.apache.flink.table.api.TableResult;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;

public class DebeziumCDC {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        EnvironmentSettings streamSettings = EnvironmentSettings.newInstance()
                .inStreamingMode().useBlinkPlanner().build();
        StreamTableEnvironment tableEnvironment = StreamTableEnvironment.create(env, streamSettings);
        tableEnvironment.getConfig().setSqlDialect(SqlDialect.DEFAULT);
        // debezium 捕获到变化的数据会写入到这个 topic 中
        String topicName = "fullfillment.inventory.customers";
        String bootStrpServers = "11.11.1.108:9092";
        String groupID = "testGroup";

        // 目标数据库地址
        String url = "jdbc:mysql://11.11.1.108:3306/flinkdemo";
        String userName = "root";
        String password = "123.com";
        String mysqlSinkTable = "customers_copy";


        // 创建一个 Kafka 数据源的表
        tableEnvironment.executeSql("CREATE TABLE customers (\n" +
                " id int,\n" +
                " first_name STRING,\n" +
                " last_name STRING,\n" +
                " email STRING \n" +
                ") WITH (\n" +
                " 'connector' = 'kafka',\n" +
                " 'topic' = '" + topicName + "',\n" +
                " 'properties.bootstrap.servers' = '" + bootStrpServers + "',\n" +
                " 'debezium-json.schema-include' = 'true',\n" +
                " 'properties.group.id' = '" + groupID + "',\n" +
                " 'format' = 'debezium-json'\n" +
                ")");

        // 创建一个写入数据的 sink 表
        tableEnvironment.executeSql("CREATE TABLE customers_copy (\n" +
                " id int,\n" +
                " first_name STRING,\n" +
                " last_name STRING,\n" +
                " email STRING, \n" +
                " PRIMARY KEY (id) NOT ENFORCED\n" +
                ") WITH (\n" +
                " 'connector' = 'jdbc',\n" +
                " 'url' = '" + url + "',\n" +
                " 'username' = '" + userName + "',\n" +
                " 'password' = '" + password + "',\n" +
                " 'table-name' = '" + mysqlSinkTable + "'\n" +
                ")");
        String updateSQL = "insert into customers_copy select * from customers";
        TableResult result = tableEnvironment.executeSql(updateSQL);

        // Block get results
        result.getJobClient().get().getJobExecutionResult(DebeziumCDC.class.getClassLoader()).get();
    }
}
