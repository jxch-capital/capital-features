FROM openjdk:17

ENV TZ=Asia/Shanghai
ENV LANG=C.UTF-8
ENV LC_ALL=C.UTF-8

ENV PGSQL_DATASOURCE_URL=pgsql:5432
ENV DB_TABLE=capital-features
ENV DB_USERNAME=capital-features
ENV DB_PASSWORD=capital-features
ENV VEC_DB_URL=vec
ENV MAIL_USERNAME=mail
ENV MAIL_PASSWORD=password
ENV USERNAME=username
ENV PASSWORD=password
ENV GEMINI_KEY=gemini_key
ENV WEB_DRIVER=selenium-hub:4444
ENV TRANS_URL=trans
ENV MINIO_URL=minio
ENV MINIO_ACCESS_KEY=minio
ENV MINIO_SECRET_KEY=minio

VOLUME /capital/features
ADD target/capital-features.jar app.jar
ADD fonts /usr/share/fonts/jdk_fonts

RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
RUN fc-cache -fvll
RUN bash -c 'touch /app.jar'
EXPOSE 8080

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom", "-Dspring.profiles.active=docker", "-Djava.awt.headless=true","-Dfile.encoding=UTF-8", "-jar","/app.jar"]
