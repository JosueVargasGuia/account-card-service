FROM openjdk:11
EXPOSE  8096
WORKDIR /app
ADD   ./target/*.jar /app/account-card-service.jar
ENTRYPOINT ["java","-jar","/app/account-card-service.jar"]