package com.mtech.util;

import com.csvreader.CsvReader;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class CsvUtil {

    public static void main(String[] args) {
        try {
            // 用来保存数据
            ArrayList<String[]> csvFileList = new ArrayList<String[]>();
            // 定义一个CSV路径
            String csvFilePath = "D:\\2021-data\\1.1\\data_A101_party1.csv";
            // 创建CSV读对象 例如:CsvReader(文件路径，分隔符，编码格式);
            CsvReader reader = new CsvReader(csvFilePath, ' ', StandardCharsets.UTF_8);
            // 跳过表头 如果需要表头的话，这句可以忽略
            // 逐行读入除表头的数据
            while (reader.readRecord()) {
                System.out.println(reader.getRawRecord());
                csvFileList.add(reader.getValues());
            }
            reader.close();

            // 遍历读取的CSV文件
            for (int row = 0; row < csvFileList.size(); row++) {
                // 取得第row行第0列的数据
                String cell = csvFileList.get(row)[0];
                System.out.println("------------>" + cell);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
