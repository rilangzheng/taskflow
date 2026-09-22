# 第一阶段：编译项目
FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn clean package -DskipTests

# 第二阶段：仅运行打包好的 JAR
FROM eclipse-temurin:17-jre

WORKDIR /app

COPY --from=build /app/target/taskflow-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]