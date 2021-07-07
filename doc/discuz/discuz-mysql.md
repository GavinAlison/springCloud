
```
create database discuz;
CREATE USER 'discuz'@'%' IDENTIFIED BY 'discuz';
GRANT ALL ON 'discuz'.* TO 'discuz'@'%';
GRANT SELECT, REPLICATION CLIENT, REPLICATION SLAVE on *.* to 'discuz'@'%'; 
flush privileges;
```

