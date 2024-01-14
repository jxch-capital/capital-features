FROM openjdk:17-alpine

ENV TZ=Asia/Shanghai
ENV DATASOURCE_URL=jdbc:postgresql://localhost:15432/capital-features
ENV VEC_DB_URL=http://127.0.0.1:5000/search_api

VOLUME /capital/features
ADD target/capital-features.jar app.jar

RUN bash -c 'touch /app.jar'

EXPOSE 8080

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
