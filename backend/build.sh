#!/bin/bash

echo "Compiling the TCF Spring BACKEND within a multi-stage docker build"

docker build --build-arg JAR_FILE=server-*.jar -t teamb/mfc-spring-server .
