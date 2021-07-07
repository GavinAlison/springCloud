package com.mtech.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternUtil {

    public static boolean find(String input, String regex) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(input);
        if (m.find()) {
            return true;
        }
        return false;
    }


    public static String obtainNumber(String str) {
        String result = "-1";
        String reg = "\\w+.csv_(\\d+)$";    //提取字符串末尾的数字：封妖塔守卫71 == >> 71
        Pattern p2 = Pattern.compile(reg);
        Matcher m2 = p2.matcher(str);
        if (m2.find()) {
            result = m2.group(1);// 组提取字符串
        }
        return result;
    }

}
