package com.alison.javautil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class A {

    List<String> list = new ArrayList<>();

    static List<String> staticList = new ArrayList<>();

    public static void main(String[] args) {
        List<String> mainList = new ArrayList<>();
    }

    public void createList() {
        List<Set> normalList = new ArrayList<>(1_000_000);
        for (int i = 0; i < 1_000_000; i++) {
            normalList.add(new HashSet());
        }
        final List<String> statiInterList = new ArrayList<>();
    }
}
