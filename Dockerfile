FROM openjdk:8-alpine

ADD . /build

WORKDIR /build

RUN ./gradlew jar

COPY build/libs/bouquet-producer-0.1.jar /opt/bouquet-producer.jar
RUN rm /build -rf

ENTRYPOINT ["java", "-jar", "/opt/bouquet-producer.jar"]