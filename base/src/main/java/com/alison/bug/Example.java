package com.alison.bug;

import java.util.concurrent.atomic.AtomicInteger;

public class Example {

    public static void main(String[] args) {
//        Set<Integer> set = new HashSet<>();
//        for (int i = 0; i < 100; i++) {
//            set.add(i);
//            set.remove(i-1);
//        }
//
//        System.out.println(set);
//        System.out.println(set.size());

//        Set<Short> set = new HashSet<>();
//        for (short i = 0; i < 100; i++) {
//            set.add(i);
//            set.remove(i-1);
//        }
//        System.out.println(set);
//        System.out.println(set.size());

//        Object i = 1 == 1 ? new Integer(3) : new Float(1);
//        System.out.println(i);
//        System.out.println(new Integer(3));
        AtomicInteger integer = new AtomicInteger(1);
        for (; ; ) {
            integer.incrementAndGet();
            if (integer.get() > 100_000_000) {
                break;
            }
        }
        System.out.println(String.format("%d", integer.get()));

    }
}
