package com.alison.flink.demo;


import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;


public class SocketTextStreamWordCount {

    public static void main(String[] args) throws Exception {
        //获取运行环境
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        //数据源
        DataSet<String> text = env.fromElements("I have a good friend. She is a very nice person. The first time I saw her at school, she smiled at me and I like her a lot. When we do homework in a team, we become friends. As we share the same interest, we are close. Now we often hang out for fun and we cherish our friendship so much.");
        // transfromation
        DataSet<Tuple2<String, Integer>> wordCount = text.flatMap(new LineSplitter()).groupBy(0).sum(1);
        //打印
        wordCount.print();
//        env.execute();

        // Environment
//        执行环境 StreamExecutionEnvironment 是所有flink程序的基础
//        创建执行环境有三种方式，分别为==
//        StreamExecutionEnvironment.getExecutionEnvironment
//        StreamExecutionEnvironment.createLocalEnvironment
//        StreamExecutionEnvironment.createRemoteEnvironment



    }

    public static class LineSplitter implements FlatMapFunction<String, Tuple2<String, Integer>> {
        @Override
        public void flatMap(String line, Collector<Tuple2<String, Integer>> out) throws Exception {
            for (String word : line.split(" ")) {
                out.collect(new Tuple2<>(word, 1));
            }
        }
    }
}
