FROM openjdk:17-jdk

ENV APP_HOME=/usr/app/

WORKDIR $APP_HOME

# COPY target/Admin-0.0.1-SNAPSHOT.jar application.jar

EXPOSE 8088

CMD ["java", "-jar", "Admin-0.0.1-SNAPSHOT.jar"]
