#!/bin/bash

DIR=$(dirname ${0})
cd ${DIR}
cd ../

mvn clean compile -DskipTests
mvn -P mtdemo flyway:clean flyway:migrate
