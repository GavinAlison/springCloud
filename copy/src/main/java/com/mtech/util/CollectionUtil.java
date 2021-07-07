package com.mtech.util;

import com.google.common.base.Splitter;
import lombok.experimental.UtilityClass;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class CollectionUtil {

    public static boolean isEmpty(Collection collection) {
        return collection == null || collection.isEmpty();
    }

    public static List<String> splitToList(String str, String separator) {
        return Splitter.on(separator).trimResults().omitEmptyStrings().splitToList(str);
    }

    public static List<String> splitToListWithLF(String str) {
        return splitToList(str, APPConstant.LF);
    }

    public static String list2StringWithLF(List<String> list) {
        return list.stream().collect(Collectors.joining(APPConstant.LF));
    }
}
