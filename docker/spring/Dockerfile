FROM openjdk:11
MAINTAINER Eddy eddy@teamo2.kr

EXPOSE 8090
CMD ["./mvnw", "clean", "package", "-DskipTests"]

ARG JAR_FILE_PATH=target/*.jar
COPY ${JAR_FILE_PATH} businessWork.jar

ENTRYPOINT ["java", "-jar", "businessWork.jar"]