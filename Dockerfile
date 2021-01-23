
FROM openjdk:11

EXPOSE 3000

ADD target/WebsocketWebchat-0.0.1-SNAPSHOT.jar WebsocketWebchat-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java","-jar","/WebsocketWebchat-0.0.1-SNAPSHOT.jar"]
