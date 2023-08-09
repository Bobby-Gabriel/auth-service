#Build gradle stage

FROM gradle:6.6.1 AS BUILD
WORKDIR /app/
COPY . .
RUN gradle clean build

#Package stage

FROM eclipse-temurin:17-jdk-alpine
ENV JAR_NAME=auth-service.jar
ENV APP_HOME=/app/
WORKDIR $APP_HOME
COPY --from=BUILD $APP_HOME .
EXPOSE 9001
ENTRYPOINT exec java -jar $APP_HOME/build/libs/$JAR_NAME