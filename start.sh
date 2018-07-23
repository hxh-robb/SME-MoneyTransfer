#!/bin/bash

DIR=`dirname $(readlink -f $0)`
cd $DIR

./stop.sh
./build.sh
docker-compose -f docker-compose.deps.yaml -f docker-compose.yaml up -d