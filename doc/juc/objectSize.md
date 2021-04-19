# object size
## 如何计算对象的大小?

一个空对象的大小默认是16(4+4+4+4) 字节

public class B {
    private long s;
}
12+8 --- 3*8=24 字节

public class C {
    private int a;
    private long s;
}
12+8+4 = 24字节

int[] aa = new int[0];
12+4+0=16字节



## java对象的内存布局， 对象与数组

实例对象：
-   mark word
-   class pointer
-   实例数据
-   内存填充

数组对象
-   mark word
-   class pointer
-   数组长度
-   实例数据
-   内存填充  (8的倍数)

数组对象与实例对象的区别，就在于数组对象多一个数组长度， 
数组对象的class pointer 指向的是数组类型，
实例对象的class pointer指向的是类的class信息


> link: https://www.jianshu.com/p/6d62c3ee48d0
>https://zhuanlan.zhihu.com/p/151856103
