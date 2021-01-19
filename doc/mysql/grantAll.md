# 赋权
mysql -u root -p

> grant all privileges on `any_data_hub2.0`.*  to 'any'@'%' identified by 'anywd1234' ;
> flush privileges;


show processlist;

# 问题1： 赋权失败
原因： 存在了多个不同host的root进程，所以在对root用户grant all操作刷新后，由于多个host的root用户连接，并不知道哪个root进程执行了grant all操作，所以在对AA用户分配库权限的时候报错root没有权限。

解决： 将所有的root相关进程都给结束掉


SELECT concat('KILL ',id,';') FROM information_schema.processlist WHERE user='root';

kill id;

## 修改连接数
SHOW VARIABLES LIKE '%connections%';
SHOW STATUS LIKE 'Thread%';

最大连接数：set global max_connections=1000;
最大错误连接数set global max_connect_errors = 100;

方式2：
```text
# 修改最大连接数
max_connections=1000
# 修改最大错误连接数
max_connect_errors = 1000
```


