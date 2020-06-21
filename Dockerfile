FROM openjdk:11-jre-slim

ENV TZ=America/Sao_Paulo
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

RUN mkdir /opt/movie-challenge

WORKDIR /opt/movie-challenge

COPY ./target/movie-challenge*.jar movie-challenge.jar

SHELL ["/bin/bash","-c"]

EXPOSE 8080
EXPOSE 5005

ENTRYPOINT java ${ADDITIONAL_OPTS} -jar movie-challenge.jar
