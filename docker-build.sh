#!/bin/bash

DIR=`dirname $(readlink -f $0)`

cd $DIR

# Build Spring-Boot executable jar
./mvnw -DskipTests=true clean package

# Build Docker image
docker build -f ./Dockerfile . -t sme-mts:0.0.0