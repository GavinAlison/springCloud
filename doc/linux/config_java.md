# linux下查找java的安装目录
```
java -version
echo $JAVA_HOME
which java 获取地址
ll /usr/bin/java
ll /etc/alternatives/java
ll /usr/lib/jvm/java-1.8.0-openjdk-1.8.0.272.b10-3.el8_3.x86_64/
cd /usr/lib/jvm/java-1.8.0-openjdk-1.8.0.272.b10-3.el8_3.x86_64/

vi /etc/profile
在文件尾添加文字
----
export JAVA_HOME=/usr/lib/jvm/java-1.8.0-openjdk-1.8.0.272.b10-3.el8_3.x86_64/
export PATH=$JAVA_HOME/bin:$PATH
export CLASSPATH=.:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar
----

source /etc/profile

```