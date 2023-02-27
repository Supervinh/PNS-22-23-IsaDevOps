#!/bin/bash

echo "Compiling the TCF Spring CLI within a multi-stage docker build"

docker build --build-arg JAR_FILE=cli-0.1.jar -t teamb/mfc-spring-cli .
#TODO script or arg to avoid changing the version number in the build script in 3 different places