FROM openjdk:11
MAINTAINER Eddy eddy@teamo2.kr

CMD ["./mvnw", "clean", "package", "-DskipTests"]

EXPOSE 8090

ARG JAR_FILE_PATH=target/*.jar
COPY ${JAR_FILE_PATH} "BusinessWork.jar"

ENTRYPOINT ["java", "-jar", "BusinessWork.jar"]