FROM eclipse-temurin:21

WORKDIR /app

COPY . .

RUN apt-get update && apt-get install -y maven

RUN mvn clean package -DskipTests

EXPOSE 8083

CMD ["java", "-jar", "target/pmajay-backend-1.0.0.jar"]
