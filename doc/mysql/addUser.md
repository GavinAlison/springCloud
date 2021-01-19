add user
```
CREATE USER 'any'@'%' IDENTIFIED BY 'any123';
GRANT ALL ON 'any_data_hub2.0'.* TO 'any'@'%';
GRANT SELECT, REPLICATION CLIENT, REPLICATION SLAVE on *.* to 'any'@'%'; 
flush privileges;
```

修改root密码

1. mysqld
```text
mysqld --skip-grant-tables 
use mysql;
update user set password=password("root") where user="root";
flush privileges;
```

2. mysqladmin
```text
mysqladmin -uroot -proot password 123
```
3. update 
```text
use mysql;
update user set password=password('123') where user='root' and host='localhost';
flush privileges;
```

## mysql 5.7.30 修改密码

```sql
update mysql.user set authentication_string=password('root.123') where  user='root' and Host = 'localhost';
flush privileges;
```






