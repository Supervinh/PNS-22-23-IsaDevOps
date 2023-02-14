#!/bin/bash

echo "Compiling the TCF Spring CLI within a multi-stage docker build"

docker build --build-arg JAR_FILE=cli-1.0-SNAPSHOT.jar -t teamb/mfc-spring-cli .
