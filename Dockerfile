FROM gradle:6.6.1-jdk8 as compile

WORKDIR /home/gradle/project

# Only copy dependency-related files
COPY build.gradle gradle.properties settings.gradle /app/

# Only download dependencies
# Eat the expected build failure since no source code has been copied yet
RUN gradle clean build --no-daemon > /dev/null 2>&1 || true

COPY . /home/gradle/project
RUN gradle bootJar

FROM java:8
COPY --from=compile /home/gradle/project/build/libs/cockstalgia-core-0.0.1-SNAPSHOT.jar .
ENTRYPOINT ["java","-jar","./cockstalgia-core-0.0.1-SNAPSHOT.jar"]
EXPOSE 8080