#!/bin/bash

REPOSITORY=/root/app
echo "REPOSITORY = $REPOSITORY"
cd $REPOSITORY

PROJECT_NAME=lonessum
echo "PROJECT_NAME = $PROJECT_NAME"

PROJECT_PID=$(pgrep -f $PROJECT_NAME)
echo "PROJECT_PID = $PROJECT_PID"

if [ -z $PROJECT_PID ]; then
    echo "no running project"
else
    kill -9 $PROJECT_PID
    sleep 3
fi

JAR_NAME="lonessum.jar"
echo "JAR_NAME = $JAR_NAME"

nohup /usr/lib/jvm/jdk-11/bin/java -jar $REPOSITORY/$JAR_NAME 1>/dev/null 2>&1 &
