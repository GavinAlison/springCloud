package com.mtech.util;


import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


@Slf4j
public class FileUtil {

    public static void main(String[] args) throws IOException, InterruptedException {
        String csvFilePath = "D:\\2021-data\\1.1\\data_A101_party1.csv";
        csvFilePath = "/home/huangyong/text";
//        csvFilePath = "/home/huangyong/data/1.1/data_A101_party1.csv";
        long fileCount = Files.lines(new File(csvFilePath).toPath()).count();
        int i = 0;
        long count = fileCount / 10000 + (fileCount % 10000 == 0 ? 0 : 1);
        do {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(csvFilePath), "UTF-8"))) {
                List<String> result = new ArrayList<>();
                while (in.readLine() != null) {
                    String line = in.readLine();
                    result.add(line);
                    if (result.size() == 10000) {
                        break;
                    }
                }
                System.out.println(result);
            }
            System.out.println("=================");
            i++;
        } while (i < count);
        TimeUnit.SECONDS.sleep(10);
    }

    public static List<String> readLines(InputStream stream) throws Exception {
        return readLines(stream, StandardCharsets.UTF_8);
    }

    public static List<String> readLines(InputStream stream, Charset charset) throws Exception {
        List<String> contents = Lists.newArrayList();
        try (InputStreamReader inputStreamReader = new InputStreamReader(stream, charset);
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                contents.add(line);
            }
        }
        return contents;
    }

    public static List<String> readLines(String filePath, int page, int size) throws Exception {
        List<String> result = new ArrayList<>();
        try (InputStreamReader fReader = new InputStreamReader(new FileInputStream(filePath), "UTF-8");
             BufferedReader reader = new BufferedReader(fReader)) {
            int i = 1;
            String str = null;
            while (i++ < page * size + 1) {
                reader.readLine();
            }
            // 遇到id字符过滤, 不考虑，避免后期行号混乱
//            String firstline = reader.readLine();
//            log.info("firstline==> {}, !\"id\".equals(firstline) =={}", firstline, !"id".equals(firstline));
//            if (Strings.isNotBlank(firstline) && !"id".equals(firstline)) {
//                result.add(firstline);
//            }
            while ((str = reader.readLine()) != null) {
                result.add(str);
                if (result.size() == size) {
                    break;
                }
            }
        }
        return result;
    }

    public static String formatFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        String wrongSize = "0B";
        if (fileS == 0) {
            return wrongSize;
        }
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "GB";
        }
        return fileSizeString;
    }

    public static boolean writeWithMapperByteBuffer(String path, List<String> context) {
        String collect = context.stream().collect(Collectors.joining(APPConstant.LF));
        collect = collect + APPConstant.LF;
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(path, "rw");
             FileChannel channel = randomAccessFile.getChannel();) {
            File destFile = new File(path);
            if (!destFile.exists()) {
                destFile.createNewFile();
            }
            MappedByteBuffer mapBuffer = channel.map(FileChannel.MapMode.READ_WRITE, randomAccessFile.length(), collect.length());
            mapBuffer.put(collect.getBytes("UTF-8"));
            mapBuffer.flip();
//            if(mapBuffer.isDirect()){
//                ((DirectBuffer)mapBuffer).cleaner().clean();
//            }else{
//                System.gc();
//            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean readWithMapperByteBuffer(String path, List<String> context) {
        String collect = context.stream().collect(Collectors.joining(APPConstant.LF));
        collect = collect + APPConstant.LF;
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(path, "rw");
             FileChannel channel = randomAccessFile.getChannel();) {
            MappedByteBuffer mapBuffer = channel.map(FileChannel.MapMode.READ_WRITE, randomAccessFile.length(), collect.length());
            mapBuffer.put(collect.getBytes("UTF-8"));
            mapBuffer.flip();
//            if(mapBuffer.isDirect()){
//                ((DirectBuffer)mapBuffer).cleaner().clean();
//            }else{
//                System.gc();
//            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static void writeStringToFile(String destPath, String encrypts) {
        writeStringToFile(destPath, encrypts, true);
    }

    public static void writeStringToFileNoLF(String destPath, String encrypts) {
        writeStringToFile(destPath, encrypts, false);
    }
    public static void writeStringToFile(String destPath, String encrypts, boolean appendLF) {
        try {
            File destFile = new File(destPath);
            if (!destFile.exists()) {
                destFile.createNewFile();
            }
            if (appendLF) {
                encrypts += APPConstant.LF;
            }
            FileUtils.writeStringToFile(destFile, encrypts, StandardCharsets.UTF_8, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> readlines(String path) throws Exception {
        return FileUtils.readLines(new File(path), "UTF-8");
    }
}
