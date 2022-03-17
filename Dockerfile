FROM openjdk:11-jdk
EXPOSE 8086
ENTRYPOINT ["java","-jar","/rest-0.0.1.jar"]

