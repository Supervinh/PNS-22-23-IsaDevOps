FROM maven:3.8.7-eclipse-temurin-17-alpine
WORKDIR /app

RUN mkdir -p ~/.local/bin \
    && mkdir -p ~/.m2


