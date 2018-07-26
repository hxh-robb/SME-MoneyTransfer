#!/bin/bash

DIR=`dirname $(readlink -f $0)`
cd $DIR

./stop.sh
./build.sh
sudo docker-compose -p demo -f docker-compose.deps.yaml -f docker-compose.yaml up -d