package com.mtech.util;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ShellExecuteUtil {

    private static final String SPLIT_COMMAND = "split -l 1000000 -d ";

    private static final String COPY_COMMAND = "cp ";


    public static boolean splitFile(String fileName) throws Exception {
        String shell = generateSplitStr(fileName, SPLIT_COMMAND);
        return executeSh(shell);
    }

    public static boolean cpFile(String fileName) throws Exception {
        String shell = generateMV(fileName, COPY_COMMAND);
        System.out.println(shell);
        return executeSh(shell);
    }

    private static List<String> extractStr(String fileName) throws IOException {
        List<String> result = new ArrayList<>(2);
        String parentPath = fileName.substring(0, fileName.lastIndexOf(File.separator) + 1);
        File file = new File(parentPath + APPConstant.ORIGIN);
        if (!file.exists()) {
            file.mkdir();
        }
        String simpleName = fileName.substring(fileName.lastIndexOf(File.separator) + 1);
        result.add(file.getCanonicalPath());
        result.add(simpleName);
        return result;
    }

    private static String generateMV(String fileName, String executeSh) throws IOException {
        List<String> fileList = extractStr(fileName);
        return executeSh + fileName + " " + fileList.get(0) + File.separator + fileList.get(1);
    }

    private static String generateSplitStr(String fileName, String executeSh) throws IOException {
        String parentPath = fileName.substring(0, fileName.lastIndexOf(File.separator) + 1);
        File file = new File(parentPath + APPConstant.ORIGIN);
        if (!file.exists()) {
            file.mkdir();
        }
        String simpleName = fileName.substring(fileName.lastIndexOf(File.separator) + 1);
        return executeSh + fileName + " " + file.getCanonicalPath() + File.separator + simpleName + "_";
    }

    public static boolean executeSh(String exesh) throws IOException {
        BufferedReader stdInput = null;
        BufferedReader stdError = null;
        try {
            Process process = null;
            process = Runtime.getRuntime().exec(exesh);
            String[] result = new String[2];
            stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
            stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            while ((result[0] = stdInput.readLine()) != null) {
                System.out.println(result[0]);
            }
            while ((result[1] = stdError.readLine()) != null) {
                System.out.println(result[1]);
            }
            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (stdInput != null) {
                stdError.close();
            }
            if (stdError != null) {
                stdError.close();
            }
        }
        return true;
    }

    public static String executeShForOut(String exesh) throws IOException {
        BufferedReader stdInput = null;
        BufferedReader stdError = null;
        String output = "";
        try {
            Process process = null;
            String[] cmd = new String[]{"sh", "-c", exesh};
            process = Runtime.getRuntime().exec(cmd);
            String[] result = new String[2];
            stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
            stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            while ((result[0] = stdInput.readLine()) != null) {
                output = result[0];
            }
            while ((result[1] = stdError.readLine()) != null) {
                System.out.println(result[1]);
            }
            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
            return output;
        } finally {
            if (stdInput != null) {
                stdError.close();
            }
            if (stdError != null) {
                stdError.close();
            }
        }
        return output;
    }

    public static long executeShForTime(String exesh) throws IOException {
        BufferedReader stdInput = null;
        BufferedReader stdError = null;
        StopWatch stopWatch = new StopWatch();
        try {
            Process process = null;
            stopWatch.start("uniq file");
            process = Runtime.getRuntime().exec(exesh);
            String[] result = new String[2];
            stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
            stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            while ((result[0] = stdInput.readLine()) != null) {
                System.out.println(result[0]);
            }
            while ((result[1] = stdError.readLine()) != null) {
                System.out.println(result[1]);
            }
            process.waitFor();
            stopWatch.stop();
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        } finally {
            if (stdInput != null) {
                stdError.close();
            }
            if (stdError != null) {
                stdError.close();
            }
        }
        return stopWatch.getTotalTimeMillis();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        String shellCommand = "sed -n '325312p' /home/huangyong/data/client/source/data_50w.csv";
        System.out.println(executeShForOut(shellCommand));
    }
}
