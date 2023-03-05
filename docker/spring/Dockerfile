FROM openjdk:11
MAINTAINER Eddy eddy@teamo2.kr

RUN apt update
RUN apt install net-tools
RUN apt install sudo
RUN apt install lsof

CMD ["./mvnw", "clean", "package", "-DskipTests"]

EXPOSE 8090

ARG JAR_FILE_PATH=target/*.jar
COPY ${JAR_FILE_PATH} "BusinessWork.jar"

CMD ["nohup", "java", "-jar", "BusinessWork.jar", "&"]