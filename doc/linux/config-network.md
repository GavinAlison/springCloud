# virtualbox 配置centos 7的网络

### 1： 网络配置
在Virtualbox虚拟机配置 网络地址转换（NAT） + Host-Only模式

网络地址转换（NAT）的mac地址： 0800271E3DDE

Host-Only模式的mac地址：080027919471

### 2: centos的配置文件

先配置上网
vi /etc/sysconfig/network-scripts/ifcfg-enp0s3
```text
NAME=eth0
DEVICE=eth0
HWADDR=08:00:27:1E:3D:DE
TYPE=Ethernet
UUID=48f940f9-be30-4a00-b660-378e9414e31a
ONBOOT=yes
BOOTPROTO=dhcp
```

### 3. 启动网络
service netwok restart

### 4. install openssh 
```text
yum install openssh -y
```

利用ip addr查看ip地址
连接ssh  

#### 5. 修改网络的名称

mv /etc/sysconfig/network-scripts/ifcfg-enp0s3 /etc/sysconfig/network-scripts/ifcfg-eth0
cp /etc/sysconfig/network-scripts/ifcfg-eth0 /etc/sysconfig/network-scripts/ifcfg-eth1

```text
NAME=eth1
DEVICE=eth1
TYPE=Ethernet
ONBOOT=yes
BOOTPROTO=static
IPADDR=192.168.56.101
NETMASK=255.255.255.0
HWADDR=08:00:27:91:94:71
```

#### 1. 修改grub配置文件，取消一致性网络设备命名
`vi /etc/default/grub`
添加GRUD_CMDLINE_LINUX信息的net.ifnames=0 biosdevname=0
```text
GRUB_CMDLINE_LINUX="crashkernel=auto spectre_v2=retpoline rd.lvm.lv=centos/root rd.lvm.lv=centos/swap rhgb quiet net.ifnames=0 biosdevname=0"
```

#### 2. 重新生成GRUB配置并更新内核参数
`grub2-mkconfig -o /boot/grub2/grub.cfg`


#### 3. 添加udev规则
`vi /etc/udev/rules.d/70-persistent-net.rule`

```
SUBSYSTEM=="net", ACTION=="add", DRIVERS=="?*", ATTR{address}=="08:00:27:1E:3D:DE", ATTR{type}=="1", KERNEL=="eth*", NAME="eth0"
SUBSYSTEM=="net", ACTION=="add", DRIVERS=="?*", ATTR{address}=="08:00:27:91:94:71", ATTR{type}=="1", KERNEL=="eth*", NAME="eth1"
```

其中要注意ATTR{type}==“1“，ATTER{address}是enp3s0和enp8s0对应的MAC地址，KERNEL和NAME就是根据自己需求去写，因为这里将网卡名改为enp8s0和enp9s0，所以是KERENL==“enp*”,NAME=“enp0”与NAME="enp1"，修改好了之后保存退出。

reboot
                           
