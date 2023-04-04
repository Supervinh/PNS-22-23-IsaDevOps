#!/bin/bash

if [ -z "$1" ]; then
  echo "Compiling the MFC Spring BACKEND within a multi-stage docker build"
  docker build --build-arg JAR_FILE=server-*.jar -t jeannestheo/mfc-spring-server .
else
  docker build --build-arg JAR_FILE=server.jar -f DeployementDockerfile -t jeannestheo/mfc-spring-server:"$1" .
fi

