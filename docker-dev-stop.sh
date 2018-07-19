#!/bin/bash

DIR=`dirname $(readlink -f $0)`

cd $DIR

docker stop sme-mts-mariadb sme-mts