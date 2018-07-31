#FROM java:8-jre
FROM robbtsang/sme:java8_python2

MAINTAINER Robb Tsang <robb@smeinternet.com>

## Copy application

WORKDIR /app

ADD ./target/mts-0.0.0.jar /app

## Host-Container environment mounting

EXPOSE 8080

## Start the application

#CMD ["/app/mts-0.0.0.jar", "--spring.profiles.active=docker"]
CMD ["/app/mts-0.0.0.jar"]