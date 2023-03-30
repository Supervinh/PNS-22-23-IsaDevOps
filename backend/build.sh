#!/bin/bash

if [ -z "$1" ]; then
  echo "Compiling the TCF Spring BACKEND within a multi-stage docker build"
  docker build --build-arg JAR_FILE=server-*.jar -t jeannestheo/mfc-spring-server .
else
  docker build --build-arg JAR_FILE=server.jar -f DeployementDockerfile -t jeannestheo/mfc-spring-server .
fi

