FROM openjdk:8
ENV APP_HOME=~/development/backenddevelopment/itemService
WORKDIR $APP_HOME
COPY ./build/libs/* ./itemService-0.0.1-SNAPSHOT.jar
CMD ["java","-jar","-Dspring.profiles.active=default","itemService-0.0.1-SNAPSHOT.jar"]
COPY ./src/main/resources/* ./