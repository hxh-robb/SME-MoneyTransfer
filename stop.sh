#!/bin/bash

DIR=`dirname $(readlink -f $0)`
cd $DIR

sudo docker-compose -p demo -f docker-compose.deps.yaml -f docker-compose.yaml down 2>/dev/null