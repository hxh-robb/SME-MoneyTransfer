#!/bin/bash

DIR=`dirname $(readlink -f $0)`
cd $DIR

./stop.sh
./build.sh
docker-compose up -d