package com.alison;

import org.junit.Test;

public class TestA {

    @Test
    public void test01(){
        Boolean a = true;
//        boolean a = true;
        Double d = Double.valueOf(a.toString());
        System.out.println( d.intValue());
    }
}
