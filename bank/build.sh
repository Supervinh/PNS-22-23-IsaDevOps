#!/bin/bash

echo "Compiling the NestJS Bank system within a multi-stage docker build"

docker build -t jeannestheo/mfc-bank-service .
