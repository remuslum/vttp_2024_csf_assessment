# Build Angular
FROM node:23 AS build_angular

WORKDIR /src

RUN npm i -g @angular/cli

COPY client/src src
COPY client/*.json .

RUN npm ci && ng build

# Build spring boot
FROM eclipse-temurin:23-jdk AS builder_java

WORKDIR /src

COPY mvnw .
COPY pom.xml .
COPY src src
COPY .mvn .mvn

# Copy the angular application over to static directory
# The * only copies files and ignore directories
COPY --from=build_angular /src/dist/client-side/browser/ src/main/resources/static

# Make mvnw executable
RUN chmod a+x mvnw
# produce target/server-0.0.1-SNAPSHOT.jar
RUN ./mvnw package -Dmaven.test.skip=true

# Deployment container
# jdk has compilers which is not needed in the third stage
FROM eclipse-temurin:23-jre 

WORKDIR /app

COPY --from=builder_java /src/target/ecommerce-0.0.1-SNAPSHOT.jar app.jar

# Set Env Variables
ENV PORT=8080

EXPOSE ${PORT}
ENTRYPOINT SERVER_PORT=${PORT} java -jar app.jar