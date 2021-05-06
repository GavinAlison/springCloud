package com.mtech.stack;

public class TestIntegerCache {
    public static void main(String[] args) {
        Integer i1 = new Integer(66);
        Integer i2 = new Integer(66);
        Integer i3 = 66;
        Integer i4 = 66;
        Integer i5 = 150;
        Integer i6 = 150;
        System.out.println(i1 == i2);//false
        System.out.println(i3 == i4);//true
        System.out.println(i5 == i6);//false
    }

}
