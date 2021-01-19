//package com.alison;
//
//import org.apache.hadoop.io.IntWritable;
//import org.apache.hadoop.io.LongWritable;
//import org.apache.hadoop.io.Text;
//import org.apache.hadoop.mapreduce.Mapper;
//
//import javax.naming.Context;
//import java.io.IOException;
//
///**
// * Mapper<KEYIN, VALUEIN, KEYOUT, VALUEOUT> 四个泛型意思：
// * Mapper<LongWritable, Text, Text, IntWritable>
// * KEYIN -> LongWritable:偏移量(存储该行在整个文件中的起始字节偏移量)
// * VALUEIN -> Text:进入数据类型
// * KEYOUT -> Text:输出数据键类型
// * VALUEOUT -> IntWritable:输出数据值类型
// */
//public class WcMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
//    private Text word = new Text();
//    private IntWritable one = new IntWritable(1);
//
//    @Override
//    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
//        //拿到一行数据,以空格切分
//        String[] words = value.toString().split(" ");
//        //遍历单词数据，将数据变成（单词，1）的形式放入上下文中（框架）
//        for (String word : words) {
//            this.word.set(word);
//            context.write(this.word, one);
//        }
//    }
//}