package com.mtech.util;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

/**
 * 时间转换工具
 */
public class DateUtil {

    public static void main(String[] args) {
        long interval = 1613620L;
        System.out.println(convertHms(interval));
    }

    public static String convertHms(long interval) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss.SSS");//初始化Formatter的转换格式。
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
        return formatter.format(interval);
    }

    public static String now() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
    }
}
