package com.alison.server.test;

// 下面的题目可以使用Java、Python、Golang 等编程语言实现，可以在自己顺手的 IDE 上完成编码，并构造用例，给出示例输出
// 实现一个 BufferedWordReader 类，实现每次调用成员函数 `String readWord()` 能从纯英文文件 novels.txt 里依次返回 1 个单词，尽可能的完善、高效（少占用内存，考虑 novels.txt 可能会比内存容量要大）。


import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

// 请补全如下 class
class BufferedWordReader {

    BufferedReader reader;

    public BufferedWordReader(String path) throws FileNotFoundException {
        FileReader fileReader = new FileReader(path);
        reader = new BufferedReader(fileReader);
        return;
    }

    private List<String> lineList = new ArrayList<>();

    private int move = -1;

    public String readWord() throws IOException {
        if (move == -1) {
            Stream<String> stream = reader.lines();
            Iterator<String> iterator = stream.iterator();
            if (iterator.hasNext()) {
                lineList = new ArrayList<>(Arrays.asList(iterator.next().split(" ")));
            }
        }
        move++;
        String word = null;
        if (lineList.isEmpty()) {
            throw new RuntimeException("文件流读取完成");
        }
        word = lineList.get(move);
        if (move == lineList.size() - 1) {
            move = -1;
            lineList.clear();
        }
        return word;
    }

}

public class ShowMeBug {
    private static void init() throws IOException {
        BufferedWriter out = new BufferedWriter(new FileWriter("novels.txt"));
        out.write("But what is the performance impact of using S3 across a network instead of using HDFS?\nThis work done by Tatsuya Kawano (@tatsuya6502) aims to answer that question by examining the performance of different combinations of query type and storage type.");
        out.close();
    }

    public static void main(String[] args) {
        try {
            init();
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }

        BufferedWordReader bwr = null;
        try {
            bwr = new BufferedWordReader("novels.txt");

            System.out.println(bwr.readWord());
            System.out.println(bwr.readWord());
            System.out.println(bwr.readWord());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
