#!/bin/bash

DIR=`dirname $(readlink -f $0)`
cd $DIR

## Build Spring-Boot executable jar
./mvnw -Dmaven.test.skip=true -DskipTests=true clean package

## Build the schema.sql
./mwb2sql.sh

## MariaDB custom configuration
mkdir -p "$DIR/target/docker-config/mariadb"
#CNF="$DIR/target/docker-config/mariadb/utf8.cnf"
#echo "[client]" >> "$CNF"
#echo "default-character-set=utf8mb4" >> "$CNF"
#echo "[mysql]" >> "$CNF"
#echo "default-character-set=utf8mb4" >> "$CNF"
#echo "[mysqld]" >> "$CNF"
#echo "collation-server = utf8mb4_unicode_ci" >> "$CNF"
#echo "init-connect='SET NAMES utf8mb4'" >> "$CNF"
#echo "character-set-server = utf8mb4" >> "$CNF"

## Build Docker image
docker build -f ./Dockerfile . -t sme-mts:0.0.0