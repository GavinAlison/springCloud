# 睡10s的方式

### 方法一：通过线程的sleep方法

```text
Thread.currentThread().sleep(1000);
```

### 方法二：TimeUnit类里的sleep方法

```text
1 TimeUnit.DAYS.sleep(1);//天
2 TimeUnit.HOURS.sleep(1);//小时
3 TimeUnit.MINUTES.sleep(1);//分
4 TimeUnit.SECONDS.sleep(1);//秒
5 TimeUnit.MILLISECONDS.sleep(1000);//毫秒
6 TimeUnit.MICROSECONDS.sleep(1000);//微妙
7 TimeUnit.NANOSECONDS.sleep(1000);//纳秒
```
