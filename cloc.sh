#!/usr/bin/env bash

CMD=`readlink -f "$0"`
DIR=`dirname "$CMD"`
cd $DIR

sudo cloc --exclude-dir=.git,.mvn,.idea,.docker,target,docs .
