#!/bin/bash
NAME=${NAME:=/kafka-admin-server-0.0.1-SNAPSHOT}
PAHSE=${PHASE:=local}
BUILD_PATH=${BUILD_PATH:=$PWD/build/libs}

java -jar -Dspring.profiles.active=${PHASE} ${BUILD_PATH}/${NAME}.jar
