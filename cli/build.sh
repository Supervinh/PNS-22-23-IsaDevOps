#!/bin/bash

if [ -z "$1" ]; then
  echo "Compiling the MFC Spring CLI within a multi-stage docker build"
  docker build --build-arg JAR_FILE=cli-*.jar -t jeannestheo/mfc-spring-cli .
else
  docker build --build-arg JAR_FILE=cli.jar -f DeployementDockerfile -t jeannestheo/mfc-spring-cli .
fi