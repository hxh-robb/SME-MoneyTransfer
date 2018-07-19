#!/bin/bash

DIR=`dirname $(readlink -f $0)`

cd $DIR


./docker-dev-stop.sh

## Run MariaDB

# Build schema.sql
./mwb2sql.sh

docker run --name sme-mts-mariadb \
-e MYSQL_ROOT_PASSWORD=secret \
-e MYSQL_DATABASE=sme-mts \
-e MYSQL_USER=dev \
-e  MYSQL_PASSWORD=devpass \
-d -p 13306:3306 --rm \
--mount type=bind,src="$DIR/target/initdb.d",dst=/docker-entrypoint-initdb.d/ \
mariadb:10.3.7

## Run application
docker run --name sme-mts \
-d -p 18080:8080 --rm \
sme-mts:0.0.0