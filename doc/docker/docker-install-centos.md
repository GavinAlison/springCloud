#!/usr/bin/bash
#Install Docker Engine on Ubuntu

sudo yum remove docker \
                  docker-client \
                  docker-client-latest \
                  docker-common \
                  docker-latest \
                  docker-latest-logrotate \
                  docker-logrotate \
                  docker-engine
sudo yum install -y yum-utils
sudo yum-config-manager \
	--add-repo \
	http://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo
# 阿里仓库
#可以查看所有仓库中所有docker版本，并选择特定版本安装
#yum list docker-ce --showduplicates | sort -r

curl -fsSL  https://download.docker.com/linux/centos/gpg | sudo apt-key add -

sudo yum install docker-ce docker-ce-cli containerd.io -y  --nogpgcheck



