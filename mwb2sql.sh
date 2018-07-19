#!/bin/bash

mkdir -p target/initdb.d

type mysql-workbench 1> /dev/null || exit 1 # MySQL workbench is not installed

export DIR=`dirname $(readlink -f $0)`

export CMD="$(type -p mysql-workbench)"
export PY_PATH="$DIR/mwb2sql.py"
export MWB_PATH="$DIR/docs/model/sme-mts.mwb"
export SQL_PATH="$DIR/target/initdb.d/schema.sql"

cd $DIR

## Generate schema creation sql script
$CMD --version
#$CMD --quit-when-done --model $MWB_PATH --run-script $PY_PATH

$CMD --model $MWB_PATH --run-python "$(cat $PY_PATH)" --quit-when-done