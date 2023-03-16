#!/bin/bash

echo "Compiling the TCF Spring CLI within a multi-stage docker build"

docker build --build-arg JAR_FILE=cli-*.jar -t jeannestheo/mfc-spring-cli .