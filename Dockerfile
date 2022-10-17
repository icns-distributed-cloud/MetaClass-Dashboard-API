FROM openjdk:17-jdk

#ENV APP_HOME=/usr/app/
#
#WORKDIR $APP_HOME

# COPY target/Admin-0.0.1-SNAPSHOT.jar application.jar

ADD target/Admin-0.0.1-SNAPSHOT.jar Admin-0.0.1-SNAPSHOT.jar

EXPOSE 8088

ENTRYPOINT ["java", "-jar", "Admin-0.0.1-SNAPSHOT.jar"]
#CMD ["java", "-jar", "Admin-0.0.1-SNAPSHOT.jar"]
