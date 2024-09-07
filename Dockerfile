# Stage 1: Sử dụng Maven để build ứng dụng
FROM maven:3.8.8-eclipse-temurin-17 AS build
WORKDIR /app

# Copy file pom.xml để tải về dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy toàn bộ mã nguồn vào container
COPY . .

# Build ứng dụng và tạo file JAR
RUN mvn clean package -DskipTests

# Stage 2: Sử dụng OpenJDK để chạy ứng dụng
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy file JAR từ bước build sang
COPY --from=build /app/target/*.jar app.jar

# Expose cổng 8080 để chạy ứng dụng Spring Boot
EXPOSE 8080

# Lệnh để chạy ứng dụng
ENTRYPOINT ["java", "-jar", "app.jar"]