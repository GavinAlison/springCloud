package com.mtech.util;


public class StringBufferUtil {
    private StringBufferUtil() {
    }

    private static StringBuffer stringBuffer = new StringBuffer();

    public static StringBufferUtil builder() {
        return new StringBufferUtil();
    }

    public StringBufferUtil append(String str) {
        stringBuffer.append(str).append(APPConstant.LT);
        return this;
    }

    public StringBufferUtil append(long str) {
        stringBuffer.append(str).append(APPConstant.LT);
        return this;
    }

    @Override
    public String toString() {
        return stringBuffer.toString();
    }
}
