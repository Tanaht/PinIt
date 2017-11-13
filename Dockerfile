FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD target/*.war app.war
ENV JAVA_OPTS=""
EXPOSE 8080
#ENTRYPOINT exec env
ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.war
