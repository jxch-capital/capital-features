FROM openjdk:17

ENV TZ=Asia/Shanghai
ENV PGSQL_DATASOURCE_URL=localhost:15432
ENV DB_TABLE=capital-features
ENV DB_USERNAME=capital-features
ENV DB_PASSWORD=capital-features
ENV VEC_DB_URL=http://127.0.0.1:5000/search_api
ENV MAIL_USERNAME=mail
ENV MAIL_PASSWORD=password
ENV USERNAME=username
ENV PASSWORD=password
ENV GEMINI_KEY=gemini_key

VOLUME /capital/features
ADD target/capital-features.jar app.jar

RUN bash -c 'touch /app.jar'
EXPOSE 8080

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom", "-Dspring.profiles.active=docker","-jar","/app.jar"]
