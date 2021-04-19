package copy;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * IO，还有NIO、Apache提供的工具类、JDK自带的文件拷贝方法
 */
public class CopyApplicationTest {

    private static final int BUFFER_SIZE = 1024;
    private final String target = "D:\\log\\copyFile\\";
    private final String source = "E:\\data\\20210403\\1kb-account.txt";//1kb
    private final String source1 = "E:\\data\\20210403\\16kb";//16kb
    private final String source2 = "E:\\data\\20210403\\338kb";//338kb
    private final String source3 = "E:\\data\\20210403\\1108kb";//1108kb
    private final String source4 = "E:\\data\\20210403\\15567kb";//15567kb
    private final String source5 = "E:\\data\\20210403\\60MB";//61442kb
    private final String source6 = "E:\\data\\20210403\\300MB.txt";//306145kb
    private final String source7 = "E:\\data\\20210403\\1.12GB.mkv";//1178137kb
    private final String source8 = "D:\\data\\UserBehavior.csv";// 3.4GB


    @Test
    public void copy2Test() throws Exception {
        try {
            File file = new File(target);
            if (!file.exists()) {
                file.mkdirs();
            }
//            long cost = copyFileForFiles(source, target);
            long cost1 = copyFileForFiles(source1, target);
            long cost2 = copyFileForFiles(source2, target);
            long cost3 = copyFileForFiles(source3, target);
            long cost4 = copyFileForFiles(source4, target);
            long cost5 = copyFileForFiles(source5, target);
            long cost6 = copyFileForFiles(source6, target);
            long cost7 = copyFileForFiles(source7, target);
//              copyFileForIO(source7, target);
//              copyFileForNIO(source7, target);
//            copyFileForNIO2(source1, target);
//            copyFileForFiles(source1, target);
//            copyFileForFileUtils(source1, target);
//            System.out.println(String.format("%d\t%d\t%d\t%d\t%d\t%d\t%d\t%d\t", cost, cost1, cost2,
//                    cost3, cost4, cost5, cost6, cost7));
            System.out.println(cost4);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCopy() throws Exception {
        try {
            File file = new File(target);
            if (!file.exists()) {
                file.mkdirs();
            }
            // 1kb
            List<String> sourceList = new ArrayList<>(10);
//            Collections.addAll(sourceList, source, source1, source2, source3, source4, source5, source6, source7, source8);
            Collections.addAll(sourceList, source, source1, source2, source3, source4, source5, source6, source7);
            StringBuffer sourceFileSb = new StringBuffer("\t\t");
            StringBuffer ioCost = new StringBuffer("IO拷贝\t");
            StringBuffer nioCost = new StringBuffer("NIO拷贝-管道\t");
            StringBuffer nio2Cost = new StringBuffer("NIO拷贝-文件内存内存映射\t");
            StringBuffer fileCopyCost = new StringBuffer("Files#copy方法\t");
            StringBuffer fileUtilsCopyCost = new StringBuffer("FileUtils#copyFile\t");
            for (String sourceFile : sourceList) {
                sourceFileSb.append(String.format("%s\t", formatFileSize(new File(sourceFile).length())));
//                ioCost.append(String.format("%d\t", copyFileForIO(sourceFile, target)));
                nioCost.append(String.format("%d\t", copyFileForNIO(sourceFile, target)));
//                nio2Cost.append(String.format("%d\t", copyFileForNIO2(sourceFile, target)));
                fileCopyCost.append(String.format("%d\t", copyFileForFiles(sourceFile, target)));
                fileUtilsCopyCost.append(String.format("%d\t", copyFileForFileUtils(sourceFile, target)));
            }
            System.out.println(sourceFileSb.toString());
            System.out.println(ioCost.toString());
            System.out.println(nioCost.toString());
            System.out.println(nio2Cost.toString());
            System.out.println(fileCopyCost.toString());
            System.out.println(fileUtilsCopyCost.toString());
//            System.out.println(formatFileSize(new File(source).length()) + "---------------------------");
//            long IO = copyFileForIO(source, target);
//            long IO1 = copyFileForIO(source1, target);
//            copyFileForNIO(source, target);
//            copyFileForNIO2(source, target);
//            copyFileForFiles(source, target);
//            copyFileForFileUtils(source, target);

            // 16kb
//            System.out.println(formatFileSize(new File(source1).length()) + "----------------------------");
//            copyFileForNIO(source1, target);
//            copyFileForNIO2(source1, target);
//            copyFileForFiles(source1, target);
//            copyFileForFileUtils(source1, target);
//            // 338kb
//            System.out.println(formatFileSize(new File(source2).length()) + "----------------------------");
//            copyFileForIO(source2, target);
//            copyFileForNIO(source2, target);
//            copyFileForNIO2(source2, target);
//            copyFileForFiles(source2, target);
//            copyFileForFileUtils(source2, target);
            // 1108kb =1.08MB
//            System.out.println(formatFileSize(new File(source3).length()) + "----------------------------");
//            copyFileForIO(source3, target);
//            copyFileForNIO(source3, target);
//            copyFileForNIO2(source3, target);
//            copyFileForFiles(source3, target);
//            copyFileForFileUtils(source3, target);
            // 15.2*1024kb = 15.2MB
//            System.out.println(formatFileSize(new File(source4).length()) + "----------------------------");
//            copyFileForIO(source4, target);
//            copyFileForNIO(source4, target);
//            copyFileForNIO2(source4, target);
//            copyFileForFiles(source4, target);
//            copyFileForFileUtils(source4, target);

            // 60*1024kb = 60MB
//            System.out.println(formatFileSize(new File(source5).length()) + "----------------------------");
//            copyFileForIO(source5, target);
//            copyFileForNIO(source5, target);
//            copyFileForNIO2(source5, target);
//            copyFileForFiles(source5, target);
//            copyFileForFileUtils(source5, target);
            // 100*1024kb = 100MB
//            System.out.println(formatFileSize(new File(source6).length()) + "----------------------------");
//            copyFileForIO(source6, target);
//            copyFileForNIO(source6, target);
//            copyFileForNIO2(source6, target);
//            copyFileForFiles(source6, target);
//            copyFileForFileUtils(source6, target);
            // 1024*1024kb = 1GB
//            System.out.println(formatFileSize(new File(source7).length()) + "----------------------------");
//            copyFileForIO(source7, target);
//            copyFileForNIO(source7, target);
//            copyFileForNIO2(source7, target);
//            copyFileForFiles(source7, target);
//            copyFileForFileUtils(source7, target);
            // 10*1024*1024kb = 10GB
//            System.out.println(formatFileSize(new File(source).length()) + "----------------------------");
//            copyFileForIO(source, target);
//            copyFileForNIO(source, target);
//            copyFileForNIO2(source, target);
//            copyFileForFiles(source, target);
//            copyFileForFileUtils(source, target);
            // 100*1024*1024kb = 100GB
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getSimpleFileName(String fileStr) {
        return fileStr.substring(fileStr.lastIndexOf(File.separator) + 1);
    }

    public static long copyFileForIO(String source, String target) {
        long start = System.currentTimeMillis();
        String fileName = getSimpleFileName(source);
        File targetFile = new File(target + File.separator + fileName);
        try {
            if (!targetFile.exists()) {
                targetFile.createNewFile();
            }
            try (InputStream in = new FileInputStream(new File(source));
                 OutputStream out = new FileOutputStream(targetFile)) {
                byte[] buffer = new byte[BUFFER_SIZE];
                int len;
                while ((len = in.read(buffer)) > 0) {
                    out.write(buffer, 0, len);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println(String.format("IO file copy exception: %s", ex.getMessage()));
        }
        long cost = System.currentTimeMillis() - start;
//        System.out.println(String.format("IO拷贝: %d ", cost));
        // TODO: todo
        targetFile.delete();
        return cost;
    }


    //
    public static long copyFileForNIOForSplit1M(String source, String target) {
        long start = System.currentTimeMillis();
        String fileName = getSimpleFileName(source);
        File targetFile = new File(target + File.separator + fileName);
        try {
            if (!targetFile.exists()) {
                targetFile.createNewFile();
            }


        } catch (Exception e) {
            System.out.println(String.format("NIOForSplit file copy exception: %s", e.getMessage()));
        }
        long cost = System.currentTimeMillis() - start;
        // TODO: todo
        targetFile.delete();
        return cost;
    }

    //NIO拷贝
    //一是通过管道，而是通过文件内存内存映射
    public static long copyFileForNIO(String source, String target) {
        long start = System.currentTimeMillis();
        String fileName = getSimpleFileName(source);
        File targetFile = new File(target + File.separator + fileName);
        try {
            if (!targetFile.exists()) {
                targetFile.createNewFile();
            }
            try (FileChannel input = new FileInputStream(new File(source)).getChannel();
                 FileChannel output = new FileOutputStream(targetFile).getChannel()) {
                output.transferFrom(input, 0, input.size());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (IOException ex) {
            System.out.println(String.format("IO file copy exception: %s", ex.getMessage()));
        }
        long cost = System.currentTimeMillis() - start;
//        System.out.println(String.format("NIO拷贝-管道:   %d ", cost));
        // TODO: todo
        targetFile.delete();
        return cost;
    }

    //文件内存映射：
    // 把内核空间地址与用户空间的虚拟地址映射到同一个物理地址，DMA 硬件可以填充对内核与用户空间进程同时可见的缓冲区了。
    // 用户进程直接从内存中读取文件内容，应用只需要和内存打交道，不需要进行缓冲区来回拷贝，大大提高了IO拷贝的效率。
    // 加载内存映射文件所使用的内存在Java堆区之外
    //NIO的内存映射实际上就是少了一次从内核空间拷贝到用户空间的过程，将对用户缓冲区的读改为从内存读取
    public static long copyFileForNIO2(String source, String target) {
        long start = System.currentTimeMillis();
        String fileName = getSimpleFileName(source);
        File targetFile = new File(target + File.separator + fileName);
        try {
            if (!targetFile.exists()) {
                targetFile.createNewFile();
            }
            try (FileInputStream fis = new FileInputStream(new File(source));
                 FileOutputStream fos = new FileOutputStream(targetFile)) {
                FileChannel sourceChannel = fis.getChannel();
                FileChannel targetChannel = fos.getChannel();
                MappedByteBuffer mappedByteBuffer = sourceChannel.map(FileChannel.MapMode.READ_ONLY, 0, sourceChannel.size());
                targetChannel.write(mappedByteBuffer);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException ex) {
            System.out.println(String.format("copy exception: %s", ex.getMessage()));
        }
        long cost = System.currentTimeMillis() - start;
//        System.out.println(String.format("NIO拷贝-文件内存内存映射:   %d ", cost));
        // TODO: todo
        targetFile.delete();
        return cost;
    }

    public static long copyFileForFiles(String source, String target) {
        long start = System.currentTimeMillis();
        String fileName = getSimpleFileName(source);
        File targetFile = new File(target + File.separator + fileName);
        try {
            File sourceFile = new File(source);
            Files.copy(sourceFile.toPath(), targetFile.toPath());
        } catch (IOException ex) {
            System.out.println(String.format("files copy exception: %s", ex.getMessage()));
        }
        long cost = System.currentTimeMillis() - start;
//        System.out.println(String.format("Files#copy方法:   %d ", cost));
        // TODO: todo
        targetFile.delete();
        return cost;
    }

    public static long copyFileForFileUtils(String source, String target) {
        long start = System.currentTimeMillis();
        String fileName = getSimpleFileName(source);
        File targetFile = new File(target + File.separator + fileName);
        try {
            if (!targetFile.exists()) {
                targetFile.createNewFile();
            }
            FileUtils.copyFile(new File(source), targetFile);
        } catch (IOException e) {
            System.out.println(String.format("FileUtils copy exception: %s", e.getMessage()));
        }
        long cost = System.currentTimeMillis() - start;
//        System.out.println(String.format("FileUtils#copyFile: %d ", cost));
        // TODO: todo
        targetFile.delete();
        return cost;
    }

    /**
     * 获取文件的大小(返回到达的最高单位)
     * <p>
     * 比如：1024Byte就不再用Byte
     * <p>
     * 直接返回1KB
     * <p>
     * 返回值精确到小数点后3位
     */

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
}
