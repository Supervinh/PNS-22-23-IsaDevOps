#!/bin/bash
docker build -t teamb/jenkins-agent-packed:1.0 .
docker compose up
