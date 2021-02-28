# lockSupport
阻塞和唤醒线程——LockSupport功能简介及原理浅析


## 1. locksupport是什么
locksupport是阻塞和唤醒线程的工具类

## 2. locksupport的应用场景是什么
阻塞线程

## 3. locksupport的注意事项
多次unpark可能出现线程阻塞的情况，
LockSupport.unpark(parkThread);


## 4. locksupport的原理是什么

网上一文章说，locksupport底层调用了c++的方法，主要实现是，
通过控制变量_count来实现线程的阻塞与释放。

_count这个值只能是0或者1,

线程阻塞需要消耗凭证(permit)，这个凭证最多只有1个。
当调用park方法时，
如果有凭证，则会直接消耗掉这个凭证然后正常退出；
但是如果没有凭证，就必须阻塞等待凭证可用；
而unpark则相反，它会增加一个凭证，但凭证最多只能有1个。


> https://www.cnblogs.com/takumicx/p/9328459.html