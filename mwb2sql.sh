#!/bin/bash

SCRIPT=$(readlink -f "$0")
DIR=$(dirname "$SCRIPT")
export OUT="$DIR/target/docker-entrypoint-initdb.d"

cd "$DIR"
mkdir -p "$OUT"

type mysql-workbench 1> /dev/null || exit 1 # MySQL workbench is not installed

export CMD="$(type -p mysql-workbench)"
export PY_PATH="$DIR/mwb2sql.py"
export MWB_PATH="$DIR/docs/model/sme-mts.mwb"
export SQL_PATH="$OUT/00_schema.sql"

## Generate schema creation sql script
$CMD --version
#$CMD --quit-when-done --model $MWB_PATH --run-script $PY_PATH

$CMD --model $MWB_PATH --run-python "$(cat "$PY_PATH")" --quit-when-done