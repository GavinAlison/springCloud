# BufferedInputStream 缓存输入流

## 介绍
```
BufferedInputStream 是缓冲输入流。它继承于FilterInputStream。
BufferedInputStream 的作用是为另一个输入流添加一些功能，例如，提供“缓冲功能”以及支持“mark()标记”和“reset()重置方法”。
BufferedInputStream 本质上是通过一个内部缓冲区数组实现的。例如，在新建某输入流对应的BufferedInputStream后，当我们通过read()读取输入流的数据时，BufferedInputStream会将该输入流的数据分批的填入到缓冲区中。每当缓冲区中的数据被读完之后，输入流会再次填充数据缓冲区；如此反复，直到我们读完输入流数据位置。
```

## 函数列表
```
BufferedInputStream(InputStream in)
BufferedInputStream(InputStream in, int size)

synchronized int     available()
void     close()
synchronized void     mark(int readlimit)
boolean     markSupported()
synchronized int     read()
synchronized int     read(byte[] buffer, int offset, int byteCount)
synchronized void     reset()
synchronized long     skip(long byteCount)
```

## 使用


## 问题
1.  为什么jvm数组的最大大小是Integer.MAX_VALUE-8
2.  BufferedInputStream的作用是什么
3.  BufferedInputStream如何使用
4.  为什么Integer.MAX_VALUE是2^31-1


```
为什么Integer.MAX_VALUE是2^31-1 （速记： 21亿）
一个 Integer 类型占 4 字节，一个字节占 8 位二进制码，因此一个 Integer 总共占 32 位二进制码。去除第一位的符号位，剩下 31 位来表示数值。
32位中后31位都是1,最高位是0,那就是2^31-1,
计算方法： 2^0=1, 2^30, 2^31, 
2^31
1000-0000 0000-0000 0000-0000 0000-0000

为什么Integer.MIN_VALUE 是-2^31
1000-0000 0000-0000 0000-0000 0000-0000
计算机存储的是反码，负数是二进制的反码+1
1000.... 的原码：
1000-0000 0000-0000 0000-0000 0000-0000


> https://blog.csdn.net/claram/article/details/76682125
> https://plumbr.io/outofmemoryerror

```


## 链接
>https://www.cnblogs.com/skywang12345/p/io_12.html
