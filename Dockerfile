FROM openjdk:18.0.1
LABEL maintainer="nmuchirinephat5@gmail.com"
ENV PORT=8100
#
ARG JAR_FILE=target/currency-conversion.jar
ADD ${JAR_FILE} currency-conversion.jar

COPY target/*.jar /opt/ccurrency-conversion.jar
ENTRYPOINT ["java","-jar","/currency-conversion.jar"]
