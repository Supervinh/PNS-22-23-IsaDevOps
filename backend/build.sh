#!/bin/bash

echo "Compiling the TCF Spring BACKEND within a multi-stage docker build"

docker build --build-arg JAR_FILE=server-0.1.jar -t teamb/mfc-spring-server .
