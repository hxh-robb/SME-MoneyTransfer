#!/bin/bash

DIR=`dirname $(readlink -f $0)`
cd $DIR

for param in $@
do
  if [ "$param" = "--rm-docker" ]; then
    RM_DOCKER='sudo rm -rf .docker'
  fi
  if [ "$param" = "--rm-log" ]; then
    RM_DOCKER='sudo rm -rf .docker/log'
  fi
done

echo "Stopping docker containers"
docker-compose -p demo -f docker-compose.deps.yaml -f docker-compose.yaml down 2>/dev/null
$RM_DOCKER
echo "Docker containers is now stopped"
