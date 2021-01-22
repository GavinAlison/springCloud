#!/usr/bin/env bash

##########################
# root.% /root
# mysql.%/mysql
###########################
if [ -d '/usr/local/mysql' ]; then
    rm -rf /usr/local/mysql/
fi

cd /opt/

#1.下载安装包
url=http://cdn.mysql.com/archives/mysql-5.7/mysql-5.7.30-linux-glibc2.12-x86_64.tar.gz
wget $url
#2.解压安装包
tar zxvf mysql-5.7.30-linux-glibc2.12-x86_64.tar.gz

#3. move path
mv mysql-5.7.30-linux-glibc2.12-x86_64 /usr/local/mysql

#4. 添加mysql用户组和mysql用户
#groups mysql
groupadd mysql
useradd -r -g mysql mysql

#5. 修改权限
cd /usr/local/mysql
mkdir data
#6.修改当前目录拥有者
chown -R root:root ./
chown -R mysql:mysql data
#7. 添加mysql为系统服务
cp support-files/mysql.server /etc/init.d/mysql

#8.修改mysql服务路径配置
flag=`cat /etc/profile | grep 'MYSQL_HOME'`
if [ -z "$flag"  ]; then
    cat >>/etc/profile <<\EOF
export MYSQL_HOME=/usr/local/mysql
export PATH=$MYSQL_HOME/bin:$PATH
EOF
fi

source /etc/profile

cd /etc/

#9. 复制配置mysql配置文件, 没有就创建文件my.cnf, location: /usr/local/mysql/
touch my.cnf
cat >my.cnf<<\EOF
[client]
port=3306
socket=/usr/local/mysql/data/mysql.socket
[mysqld]
# innodb_buffer_pool_size = 128M
basedir=/usr/local/mysql
datadir=/usr/local/mysql/data
user=mysql
character-set-server=utf8

binlog-format=row
server-id=20
log-bin=master
#server_id=
socket=/usr/local/mysql/data/mysql.socket
pid-file=/usr/local/mysql/data/mysqld.pid
log-error=/usr/local/mysql/data/mysqld-error.log
#join_buffer_size = 128M
#sort_buffer_size = 2M
#read_rnd_buffer_size = 2M
sql_mode=NO_ENGINE_SUBSTITUTION,STRICT_TRANS_TABLES
#skip-grant-tables
bind-address=0.0.0.0

#explicit_defaults_for_timestamp=true
EOF

source /etc/profile

# 10.初始化数据库
/usr/local/mysql/bin/mysqld --initialize --user=mysql

sleep 10

#11. 启动服务 mysqld_safe, 修改root密码
/usr/local/mysql/bin/mysqld_safe --skip-grant-tables &

sleep 3
/usr/local/mysql/bin/mysql -S /usr/local/mysql/data/mysql.socket -e "
use mysql;
CREATE USER 'root'@'%' IDENTIFIED BY 'root';
GRANT ALL privileges ON *.* TO 'root'@'%';
update mysql.user set authentication_string=password('root') where user='root';

flush privileges;
commit;
"

ps -aux | pgrep mysql | xargs kill -9

/usr/local/mysql/support-files/mysql.server start
sleep 5

# 12.创建用户mysql
/usr/local/mysql/bin/mysql -uroot -proot -e "
create user 'mysql'@'%' identified by 'mysql';
grant all privileges on *.* to 'mysql'@'%';
flush privileges;
"
# 关闭
systemctl stop firewalld
# 取消开机启动
systemctl disable firewalld

# 13. 启动服务mysql
/etc/init.d/mysql start

# 14.系统自启动mysql
yum install chkconfig -y
# 将服务文件拷贝到init.d下，并重命名为mysql
cp /usr/local/mysql/support-files/mysql.server /etc/init.d/mysql
chmod +x /etc/init.d/mysql
#添加服务
chkconfig --add mysql
# 显示服务列表
chkconfig --list
#如果看到mysql的服务，并且3,4,5都是on的话则成功，如果是off，则键入
chkconfig --level 345 mysqld on
reboot
#验证
netstat -na | grep 3306




