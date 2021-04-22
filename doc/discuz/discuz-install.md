# 记录
## 配置mysql
db: discuz
user: discuz@localhost
password: discuz

admin dashboard
user: admin
passwd: admin.123

## 安装xampp

下载xampp for windows,之后点击安装；

将discuz的upload文件夹拷贝到./htdocs/文件夹下，之后进行更名，
upload--> discuz,
然后启动xampp中的apache和mysql, 配置mysql的用户与组，数据库。

用户操作
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

配置后台管理员的用户与密码
admin/admin.123


## 配置phpadmin
找到phpAdmin/config.inc.php,
修改cfg['Servers'][$i]['password'] = '';
--> cfg['Servers'][$i]['password'] = 'root.123';



