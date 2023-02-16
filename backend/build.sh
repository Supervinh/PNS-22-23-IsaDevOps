#!/bin/bash

echo "Compiling the TCF Spring BACKEND within a multi-stage docker build"

docker build --build-arg JAR_FILE=server-1.0-SNAPSHOT.jar -t teamb/mfc-spring-server .
