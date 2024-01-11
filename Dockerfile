# 使用官方的Java开发镜像作为基础镜像
FROM openjdk:17-alpine

# 设置时区
ENV TZ=Asia/Shanghai

# 在容器中创建一个目录来保存我们的应用
VOLUME /capital/features

# 将编译好的jar文件复制到容器中
ADD target/capital-features.jar app.jar

# 更改容器中app.jar文件的时区
RUN bash -c 'touch /app.jar'

# 声明服务运行在8080端口
EXPOSE 8080

# 指定docker容器启动时运行jar包
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
