package com.alison;

import org.junit.Test;

public class PropertyTest {

    @Test
    public void test() {
        String property = System.getProperty("java.io.tmpdir");
        System.out.println(property);
        //C:\Users\yong\AppData\Local\Temp\
    }
}
