package com.alison.sqldemo;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;


public class FlinkSql02 {
    public static final String KAFKA_TABLE_SOURCE_DDL = "" +
            "CREATE TABLE user_behavior (\n" +
            "    user_id BIGINT,\n" +
            "    item_id BIGINT,\n" +
            "    category_id BIGINT,\n" +
            "    behavior STRING,\n" +
            "    ts TIMESTAMP\n" +
            ") WITH (\n" +
            "    'connector' = 'kafka',  -- 指定连接类型是kafka\n" +
            "    'topic' = 'mykafka', -- 之前创建的topic \n" +
            "    'properties.bootstrap.servers' = '11.11.1.108:9092',  -- broker地址\n" +
            "    'properties.group.id' = 'flink-test-0', -- 消费者组，相关概念可自行百度\n" +
            "    'format' = 'json',  -- json格式，和topic中的消息格式保持一致\n" +
            "    'scan.startup.mode' = 'earliest-offset' --指定从最早消费\n" +
            ")";
//    scan.startup.mode: specific-offsets, earliest-offset, latest-offset

    public static final String MYSQL_TABLE_SINK_DDL = "" +
            "CREATE TABLE `user_behavior_mysql` (\n" +
            "  `user_id` bigint  ,\n" +
            "  `item_id` bigint  ,\n" +
            "  `behavior` varchar  ,\n" +
            "  `category_id` bigint  ,\n" +
            "  `ts` timestamp(3) \n" +
            ")WITH (\n" +
            "  'connector' = 'jdbc', -- 连接方式\n" +
            "  'url' = 'jdbc:mysql://11.11.1.108:3306/inventory2', -- jdbc的url\n" +
            "  'table-name' = 'user_behavior',  -- 表名\n" +
            "  'driver' = 'com.mysql.cj.jdbc.Driver', -- 驱动名字，可以不填，会自动从上面的jdbc url解析 \n" +
            "  'username' = 'any', -- 顾名思义 用户名\n" +
            "  'password' = 'anywd1234' , -- 密码\n" +
            "  'sink.buffer-flush.max-rows' = '5000', -- 意思是攒满多少条才触发写入 \n" +
            "  'sink.buffer-flush.interval' = '2s' -- 意思是攒满多少秒才触发写入；这2个参数，无论数据满足哪个条件，就会触发写入\n" +
            ")";

    public static void main(String[] args) throws Exception {
        //构建StreamExecutionEnvironment
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        //构建EnvironmentSettings 并指定Blink Planner
        EnvironmentSettings bsSettings = EnvironmentSettings.newInstance().useBlinkPlanner().inStreamingMode().build();
        //构建StreamTableEnvironment
        StreamTableEnvironment tEnv = StreamTableEnvironment.create(env, bsSettings);
        //通过DDL，注册kafka数据源表
        tEnv.sqlUpdate(KAFKA_TABLE_SOURCE_DDL);
        //通过DDL，注册mysql数据结果表
        tEnv.sqlUpdate(MYSQL_TABLE_SINK_DDL);
        //将从kafka中查到的数据，插入mysql中
        tEnv.sqlUpdate("insert into user_behavior_mysql select user_id,item_id,behavior,category_id,ts from user_behavior");
        //任务启动，这行必不可少！
//        env.execute("test");
        tEnv.execute("test");
    }
}

