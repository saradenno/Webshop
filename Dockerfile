## Stage 1: Build stage
#FROM maven:3.8.4-openjdk-17 AS build
#WORKDIR /app
#COPY pom.xml .
#COPY src ./src
#RUN mvn clean package -DskipTests=true
#
## Check if the build was successful
#RUN if [ ! -f /app/target/ToDoAppDeel3-0.0.1-SNAPSHOT.jar ]; then echo "Build failed: JAR not found"; exit 1; fi
#
## Stage 2: Runtime stage
#FROM eclipse-temurin:17-jre-jammy
#WORKDIR /app
#COPY --from=build /app/target/ToDoAppDeel3-0.0.1-SNAPSHOT.jar /app/ToDoAppDeel3-0.0.1-SNAPSHOT.jar
#EXPOSE 8080
#CMD ["java", "-jar", "ToDoAppDeel3-0.0.1-SNAPSHOT.jar"]
# Stage 1: Build stage
FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests=true

# Check if the build was successful
RUN if [ ! -f /app/target/ToDoAppDeel3-0.0.1-SNAPSHOT.jar ]; then echo "Build failed: JAR not found"; exit 1; fi

# Stage 2: Runtime stage
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=build /app/target/ToDoAppDeel3-0.0.1-SNAPSHOT.jar /app/ToDoAppDeel3-0.0.1-SNAPSHOT.jar
EXPOSE 8080
CMD ["java", "-jar", "ToDoAppDeel3-0.0.1-SNAPSHOT.jar"]
