FROM openjdk:17-jdk-slim
WORKDIR /app
COPY build/libs/employeeRecordManagement-0.0.1-SNAPSHOT.jar /app/employees.jar
EXPOSE 8099
ENTRYPOINT ["java", "-jar", "employees.jar"]
