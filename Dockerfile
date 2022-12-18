FROM openjdk:11
MAINTAINER Eddy eddy@teamo2.kr

EXPOSE 8090
CMD ["./mvnw", "clean", "package", "-DskipTests"]

ARG JAR_FILE_PATH=target/*.jar
COPY ${JAR_FILE_PATH} "BusinessWork-0.0.1.jar"

ENTRYPOINT ["java", "-jar", "BusinessWork-0.0.1.jar"]