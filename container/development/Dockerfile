FROM gradle:jdk21-corretto as builder
WORKDIR /app
COPY . /app
RUN gradle clean build -x test

FROM amazoncorretto:21-alpine
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]