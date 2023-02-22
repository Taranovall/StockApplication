#stage 1
FROM maven:3.9.0 AS build
RUN mkdir -p /workspace
WORKDIR /workspace
COPY pom.xml /workspace
COPY src /workspace/src
RUN mvn -f pom.xml clean package

#stage 2
FROM amazoncorretto:17.0.6
COPY --from=build /workspace/target/*.jar stock-application.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/stock-application.jar"]
