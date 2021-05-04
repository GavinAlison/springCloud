package com.mtech.gc;

public class TestDemo {

    // 虚拟机的运行参数中加上“-verbose:gc”
    public static void main(String[] args) {
        byte[] placeholder = new byte[64 * 1024 * 1024];
        System.gc();
    }

    public void miss() {
        {
            byte[] placeholder = new byte[64 * 1024 * 1024];
        }
        System.gc();
        //发现虚拟机还是没有回收 placeholder 变量占用的 64M 内存。为什么所想非所见呢？
    }
    public void miss1() {
        {
            byte[] placeholder = new byte[64 * 1024 * 1024];
        }
        int a = 0;
        System.gc();
        //发现 placeholder 变量占用的64M内存空间被回收了，如果不理解局部变量表的Slot复用

//        第一次修改中，限定了 placeholder 的作用域，但之后并没有任何对局部变量表的读写操作，placeholder 变量
//        在局部变量表中占用的Slot没有被其它变量所复用，所以作为 GC Roots 一部分的局部变量表仍然保持着对它的关联。
//        所以 placeholder 变量没有被回收。
//
//        第二次修改后，运行到 int a = 0 时，已经超过了 placeholder 变量的作用域，此时 placeholder
//        在局部变量表中占用的Slot可以交给其他变量使用。而变量a正好复用了 placeholder 占用的 Slot，
//        至此局部变量表中的 Slot 已经没有 placeholder 的引用了，虚拟机就回收了placeholder 占用的 64M 内存空间。
    }

}