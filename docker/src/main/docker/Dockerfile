FROM openjdk:8-alpine
RUN mkdir /opt/csapp
WORKDIR /opt/csapp

COPY csapp*.jar /opt/csapp/csapp.jar

EXPOSE 8080/tcp
ENTRYPOINT ["java","-Xmx950m","-jar","csapp.jar"]