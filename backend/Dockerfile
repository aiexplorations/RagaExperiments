# backend/Dockerfile
FROM openjdk:11-slim

WORKDIR /app

COPY . .

RUN ./sbt clean compile

EXPOSE 8080

CMD ["./sbt", "run"]