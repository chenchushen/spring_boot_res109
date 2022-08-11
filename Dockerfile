FROM java:8
VOLUME /tmp
# 添加参数
ARG JAR_FILE

# 添加 Spring Boot 包
ADD target/${JAR_FILE} /app.jar
ENTRYPOINT ["java","-Dspring.profiles.active=prod","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]