package com.alison.oom;

import org.junit.Test;

public class OOMTest {

    @Test
    public void VMTest() {
        //java.lang.OutOfMemoryError: Requested array size exceeds VM limit
        /**
         * java.lang.OutOfMemoryError: Java heap space
         * java.lang.OutOfMemoryError: Java heap space
         * java.lang.OutOfMemoryError: Requested array size exceeds VM limit
         * java.lang.OutOfMemoryError: Requested array size exceeds VM limit
         */
        for (int i = 3; i >= 0; i--) {
            try {
                int[] arr = new int[Integer.MAX_VALUE - i];
                System.out.format("Successfully initialized an array with %,d elements.\n", Integer.MAX_VALUE - i);
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }
}
