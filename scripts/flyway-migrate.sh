#!/bin/bash

DIR=$(dirname ${0})
cd ${DIR}
cd ..
mvn -P mtdemo -DskipTests clean compile flyway:migrate
