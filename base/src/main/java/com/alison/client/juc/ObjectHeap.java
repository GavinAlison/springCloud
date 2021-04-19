package com.alison.client.juc;

public class ObjectHeap {
    public static void main(String[] args) {
        Account account = new Account();
//        System.out.println(ClassLayout.parseInstance(account).toPrintable());
    }

}

class Account {
    //只有一个boolean的属性，即占1byte
    boolean flag=false;
}