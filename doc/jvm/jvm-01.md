# javap 能查看什么
能查看字节码信息

# 常量池主要的是什么
字面量的值和符号引用

字面量的值有 基础类型，String修饰的字面量值地址，final,  static
符号引用有，class类，方法符号引用，字段符号引用

Class 引用
Method 引用
Field 引用

Java中常量池是什么？Java常量池的介绍--》 https://www.php.cn/java-article-410621.html

# 基础类型的值存储在哪儿


# String 类型的值存储在哪儿
存在字符串常量池中，存放的是字符串字面量
```
Integer integer1 = 127;
Integer integer2 = 127;
System.out.println(integer1 == integer2);// true
Integer integer1 = 128;
Integer integer2 = 128;
System.out.println(integer1 == integer2);// false
```

在Java中符号“==”是用来比较地址，符号“equals”默认是与符号“==”一样，都是用来比较地址的。


# == 比较的是什么， 就基础类型和引用类型， equals比较的是什么，就基础类型和引用类型

```
String string1 = "dashu";
String string3 =  new String("dashu");
System.out.println(string1==string3.intern());
```
如果改为`string1 == string3.intern()`结果为true，因为返回的是常量池里面字面值的地址。
如果常量池中没有这个“dashu”字面量，那么就先把这个字面量“dashu”值，先放入到常量表之后，再返回常量表的地址。

# String str = new String("dashu");创建了几个对象呢？

答案是：2个或者1个。

在`new String("dashu");`,如果这个“dashu”字面值已经出现在常量池中，那么就只出创建一个对象，如果没有就创建两个对象。

原理： 出现了字面量“dashu”，系统会到字符串常量池中查找是否有相同的字符串存在，如果有，就不会创建新的对象了，
否则就会用字面量值“dashu”，创建一个String对象。而new String("dashu")，有关键字new的存在，
就表示它一定会创建一个新的对象，然后调用接收String参数的构造器进行初始化。

#常量池的优点

常量池可以避免因频繁的创建和销毁对象，从而导致系统性能的降低，也实现了对象的共享，即可以节省内存空间，也可以节省运行的时间。

# final修饰的局部变量是存放在栈中还是在常量池中
存放在常量池中

首先final关键字对于变量的存储区域是没有任何影响的。
jvm规范中，类的静态变量存储在方法区，实例变量存储在堆区。
也就是说static关键字才对变量的存储区域造成影响。final关键字来修饰变量表明了该变量一旦赋值就无法更改。
在Java中你可以这样理解：所有的变量，包括基本类型和引用类型，它们的变量都是存放在栈中，
栈中的每个变量都包含类型、名称、值这些内容，只不过基本类型变量的值为一个具体的值，而引用类型的变量的
值为对象在堆中的地址。

# Comparator和Comapable的区别，如何实现数据的排序，正排序和倒序排序

# 哪些对象可以回收

# 锁class的标识是什么，如何锁住的

# 垃圾回收的依据是什么

# java <init> 的是什么， <cinit> 是什么


# Java agent的原理与实战