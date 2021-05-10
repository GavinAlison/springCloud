#!/usr/bin/env bash

# 一个数据文件准备  data_500w.csv data_5000w.csv
# 显示每条命令
#set -x
# env variable
source ./env.sh

# enviroment preparation
##client
preparationFile(){
    mkdir -p ${dataDir}/client/tmp
    mkdir -p ${dataDir}/client/output
    mkdir -p ${dataDir}/client/outputNum
    mkdir -p ${dataDir}/client/source/origin
    touch ${dataDir}/client/psikey
    touch ${dataDir}/client/time-two-encode.csv

    mkdir -p ${baseDir}/client
    ##server
    mkdir -p  ${dataDir}/server/tmp
    mkdir -p  ${dataDir}/server/source/origin
    touch  ${dataDir}/server/psikey

    touch  ${dataDir}/result/time.csv
    mkdir -p  ${dataDir}/result
    mkdir -p  ${dataDir}/resultNum
    mkdir -p  ${dataDir}/tmp
    touch  ${dataDir}/sum

    mkdir -p ${baseDir}/server
}
preparationFile
# close App and clear data
app_kill(){
    pgrep -f ${APPLICATION_MAIN} | xargs kill -9
}
app_kill

clearFile(){
    rm -rf ${dataDir}/client/tmp/*
    rm -rf ${dataDir}/client/output/*
    rm -rf ${dataDir}/client/outputNum/*
    rm -rf ${dataDir}/client/source/origin/*
    echo > ${dataDir}/client/psikey
    echo > ${dataDir}/client/time-two-encode.csv

    rm -rf ${dataDir}/server/tmp/*
    rm -rf ${dataDir}/server/source/origin/*
    echo > ${dataDir}/server/psikey

    echo > ${dataDir}/result/time.csv
    rm -rf ${dataDir}/result/*
    rm -rf ${dataDir}/resultNum/*
    echo > ${dataDir}/sum
}

clearFile

# data preparation
# cp -rf ${minfile}  ${dataDir}/client/source/
# cp -rf ${maxfile} ${dataDir}/server/source/

# untar client
if [[ -d ${baseDir}/client/${PROJECT} ]]; then
    rm -rf ${baseDir}/client/${PROJECT}
fi
tar -zxvf ${baseDir}/test-demo-1.0-SNAPSHOT.tar.gz  -C ${baseDir}/client/

sleep 2
# untar server
if [[ -d ${baseDir}/server/${PROJECT} ]]; then
    rm -rf ${baseDir}/server/${PROJECT}
fi
tar -zxvf ${baseDir}/test-demo-1.0-SNAPSHOT.tar.gz  -C ${baseDir}/server/

sleep 2

# client startup
cd ${baseDir}/client/${PROJECT}
sh sbin/cstartup.sh
sleep 1
# server startup
cd ${baseDir}/server/${PROJECT}
sed -i 's/9000/9001/g' ${baseDir}/server/test-demo-1.0-SNAPSHOT/config/application.properties
sh sbin/sstartup.sh
sleep 1

#go home
cd ${baseDir}

# check data encode over
flag="true"

function waitEncode(){
    while ${flag} == "true";
    do
        # 没有拆分数据的时候
        clientOverFlag=''
        serverOverFlag=''
        cliTotalFlag=$(wc -l ${dataDir}/client/tmp/* | grep 'total')
        serTotalFlag=$(wc -l ${dataDir}/server/tmp/* | grep 'total')
        if [ "$cliTotalFlag" == '' ]; then
            clientOverFlag=$(wc -l ${dataDir}/client/tmp/* | awk '{print $1}')
        else
            clientOverFlag=$(wc -l ${dataDir}/client/tmp/* | grep 'total' | awk '{print $1}')
        fi
        if [ "$serTotalFlag" == '' ]; then
            serverOverFlag=$(wc -l ${dataDir}/server/tmp/* | awk '{print $1}')
        else
            serverOverFlag=$(wc -l ${dataDir}/server/tmp/* | grep 'total' | awk '{print $1}')
        fi
        if [[ ${clientOverFlag} == "${clientNum}" ]] && [[ ${serverOverFlag} == "${serverNum}" ]]; then
            flag="false"
        fi
        sleep 30
    done
}

function main_run(){
    if [[ ${flag} == 'true' ]]; then
        curl ${deploy_url}/multitwoencode
        # wait
        sleep 1
        curl ${deploy_url}/multiuniq
        sleep 1
        curl ${deploy_url}/uniqSize
        curl ${deploy_url}/intersection
    fi
}

# data check
function data_check(){
    sort ${dataDir}/client/source/${minfile} ${dataDir}/server/source/${maxfile} | uniq -d > ${dataDir}/tmp/source.psi
    originUniqSize=$(wc -l ${dataDir}/tmp/source.psi)
    encodeUniqSize=$(wc -l ${dataDir}/resultNum/* | grep 'total' | awk '{print $1}')
    if [[ ${encodeUniqSize} == ${originUniqSize} ]]; then
        ehco "求交数据数量正确"
    fi
}

waitEncode
main_run
data_check

