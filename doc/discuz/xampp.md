## 1. 下载xampp for windows
安装xampp，对mysql维护

```
use mysql;
update user set password=password('root.123') where user='root' and host='localhost';
flush privileges;

CREATE USER 'root'@'%' IDENTIFIED BY 'root.123';
grant all privileges on *.* to 'root'@'%';

drop user 'discuz'@'%';
drop user 'discuz'@'localhost';
drop database discuz;
create database discuz;
CREATE USER 'discuz'@'%' IDENTIFIED BY 'discuz';
CREATE USER 'discuz'@'localhost' IDENTIFIED BY 'discuz';
GRANT ALL ON discuz.* TO 'discuz'@'%';
GRANT ALL ON discuz.* TO 'discuz'@'localhost';
GRANT SELECT, REPLICATION CLIENT, REPLICATION SLAVE on *.* to 'discuz'@'%'; 
GRANT SELECT, REPLICATION CLIENT, REPLICATION SLAVE on *.* to 'discuz'@'localhost'; 
flush privileges;
```


