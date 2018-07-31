#!/bin/bash

export DIR=`dirname $(readlink -f $0)`
cd $DIR

APP='-f docker-compose.yaml'
for param in $@
do
  if [ "$param" = "--no-app" ]; then
    APP=''
  fi
done


./stop.sh
if [ "$APP" != "" ]; then
  ./build.sh
fi
sudo docker-compose -p demo -f docker-compose.deps.yaml $APP up -d