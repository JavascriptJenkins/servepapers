#!/bin/sh

killall java -9
rm -rf data
rm -rf logs
rm -rf collegeapp-0.0.1-SNAPSHOT.jar
mkdir data
mkdir logs
echo "deleted the stuff"