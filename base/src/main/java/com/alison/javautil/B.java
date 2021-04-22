package com.alison.javautil;

public class B {
    public static void main(String[] args) throws Exception {
        int sum = 0;
        int count = 1000000;
        //warm up
        for (int i = 0; i < count ; i++) {
            sum += fn(i);
        }

        Thread.sleep(500);

        for (int i = 0; i < count ; i++) {
            sum += fn(i);
        }

        System.out.println(sum);
        System.in.read();
    }

    private static int fn(int age) {
        User user = new User(age);
        int i = user.getAge();
        return i;
    }
}

class User {
    private final int age;

    public User(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }
}