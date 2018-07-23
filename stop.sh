#!/bin/bash

DIR=`dirname $(readlink -f $0)`
cd $DIR

docker-compose -f docker-compose.deps.yaml -f docker-compose.yaml down 2>/dev/null