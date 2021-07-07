#!/usr/bin/env bash

clientNum='10000'
serverNum='100000'
baseDir=/home/huangyong
dataDir="${baseDir}/data"
minfile=data_1w.csv
maxfile=data_10w.csv

clientTwoencodePath=${dataDir}/client/output/
serverEncodePath=${dataDir}/server/tmp/
resultPath=${dataDir}/result/

sumPath=${dataDir}/sum

PROJECT=test-demo-1.0-SNAPSHOT
APPLICATION_MAIN=com.rongan.PSIServerApplication

deploy_url=http://192.168.0.196:9000/
