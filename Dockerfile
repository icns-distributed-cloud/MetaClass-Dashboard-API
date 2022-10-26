# FROM openjdk:17-jdk
FROM appleboy/drone-ssh:1.6.4-linux-amd64

#ENV APP_HOME=/usr/app/
#
#WORKDIR $APP_HOME

# COPY target/Admin-0.0.1-SNAPSHOT.jar application.jar

ADD build/Admin-0.0.1-SNAPSHOT.jar Admin-0.0.1-SNAPSHOT.jar

EXPOSE 8088

ENTRYPOINT ["java", "-jar", "Admin-0.0.1-SNAPSHOT.jar"]
#CMD ["java", "-jar", "Admin-0.0.1-SNAPSHOT.jar"]
