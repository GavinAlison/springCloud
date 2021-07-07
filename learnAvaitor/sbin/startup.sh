#!/usr/bin/env bash

APP_CLASS=com.rongan.PSIServerApplication
JAVA_OPTS=-server -Xms4g -Xmx4g -Xdebug

nohup java ${JAVA_OPTS} -cp lib/*:config/  $APP_CLASS > nohup.log  2>&1 &