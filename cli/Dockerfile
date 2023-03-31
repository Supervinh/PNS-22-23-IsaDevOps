# Multi-stage build
FROM eclipse-temurin:17-jdk-jammy as builder
WORKDIR /opt/app
COPY docker/.mvn/ .mvn
COPY docker/mvnw pom.xml ./
COPY scripts ./
RUN ./mvnw -B dependency:go-offline
COPY ./src ./src
RUN ./mvnw -B verify

FROM eclipse-temurin:17-jre-jammy as runner
LABEL org.opencontainers.image.authors="TeamB-PolytechNice"
ARG JAR_FILE
RUN addgroup dockergroup; adduser --ingroup dockergroup --disabled-password --system --shell /bin/false dockeruser
WORKDIR /opt/app
COPY docker/wait-for-it.sh ./wait-for-it.sh
COPY --from=builder /opt/app/target/${JAR_FILE} ./app.jar
RUN chown -R dockeruser:dockergroup /opt/app && chmod a+x ./wait-for-it.sh
COPY scripts ./
# Starting the service (shell form of ENTRYPOINT used for substitution)
USER dockeruser
ENTRYPOINT java -jar /opt/app/app.jar --mfc.host.baseurl=$SERVER_URL
