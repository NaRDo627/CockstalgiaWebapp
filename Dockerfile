FROM gradle:5.6.4-jdk8 as compile
COPY . /home/gradle/project
WORKDIR /home/gradle/project
RUN gradle bootJar

FROM java:8
COPY --from=compile /home/gradle/project/build/libs/cockstalgia-core-0.0.1-SNAPSHOT.jar .
ENTRYPOINT ["java", "-Dspring.profiles.active=local","-jar","./cockstalgia-core-0.0.1-SNAPSHOT.jar"]
EXPOSE 8080