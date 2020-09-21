FROM gradle:5.6.4-jdk8 as compile
COPY . /home/gradle/project
WORKDIR /home/gradle/project
RUN gradle bootRun

EXPOSE 8080