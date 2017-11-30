#!/usr/bin/env sh

DIR=$( cd `dirname $0` && pwd )
cd $DIR

if [ $# -lt 1 ]; then
  echo 'change-version $version'
  exit 1
fi

./mvnw versions:set -DnewVersion=$1 -DoldVersion=* -DgroupId=inc.outwit.ft -DartifactId=*
./mvnw versions:commit
