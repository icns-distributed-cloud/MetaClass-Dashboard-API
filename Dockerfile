FROM openjdk:17-jdk

ENV APP_HOME=/usr/app/

WORKDIR $APP_HOME

COPY target/*.jar application.jar

EXPOSE 8080

CMD ["java", "-jar", "application.jar"]