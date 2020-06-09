#!/bin/bash

RUN="java"
HAMCREST="./libs/hamcrest-core-1.3.jar"
JUNIT="./libs/junit-4.11.jar"
EKMEANS="./libs/ekmeans-0.3.jar"
OUTDIR="./bin"
CLASSPATH=".:$OUTDIR:$HAMCREST:$JUNIT:$EKMEANS"

EXTRA=""

MAINCLASS="twitter/location/program/data_analysis_test"

if [ ! -d "$OUTDIR" ]; then
    echo "Class files directory does not exist: run 'compile1.sh' first, exiting...";
    exit 1;
fi


cmd=`echo "$RUN" "$EXTRA" -classpath "$CLASSPATH" "$MAINCLASS"`
echo "Run command: \"$cmd\" ";

echo "--------------------------";

$cmd

echo "--------------------------"; echo "";
