package com.mtech.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class JavaSplitUtil {

    private static final int FILE_SIZE = 1 * 1024 * 1024;
    private static final int FILE_LINE = 1_000_000;
    private static final int SAVE_SIZE = 20 * FILE_SIZE;


    public static void splitFile(String path) {
        // 拆分成每个为 1,000,000 行的文件, 大小约20M
        // 子文件下标
        int filenameExt = 0;
        int j = 0;
        File file = new File(path);
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            List<String> lineList = new ArrayList<>(FILE_LINE);
            String str = bufferedReader.readLine();
            while (str != null) {
                lineList.add(str);
                str = bufferedReader.readLine();
                int linesize = lineList.parallelStream().mapToInt(String::length).sum();
                if (++j >= FILE_LINE || linesize >= SAVE_SIZE) {
//                    write
                    FileUtil.writeWithMapperByteBuffer(file.getParent() + File.separator + "origin" + File.separator + file.getName() + "_" + (filenameExt++), lineList);
                    j = 0;
                    lineList.clear();
                }
            }
            FileUtil.writeWithMapperByteBuffer(file.getParent() + File.separator + "origin" + File.separator + file.getName() + "_" + (filenameExt++), lineList);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
