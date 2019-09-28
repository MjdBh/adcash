FROM maven:3.5-jdk-8-alpine AS build-env
WORKDIR /app
RUN pwd & ls -lh
COPY . .

RUN  mvn clean package -Dmaven.test.skip=true

############################
FROM openjdk:8-jre-alpine
EXPOSE 8080
ENV TZ=Asia/Tehran
RUN export MVN_VERSION=$(mvn -q -Dexec.executable=echo -Dexec.args='${project.version}' --non-recursive  exec:exec)
RUN apk add --update tzdata && rm -rf /var/cache/apk/*
RUN mkdir -p /root/.m2 && chmod -R g+rws,a+rx /root/.m2
WORKDIR /usr/local/app/

COPY --from=build-env /app/target/*.jar /usr/local/app/
RUN mv /usr/local/app/challenge*.jar /usr/local/app/challenge.jar
CMD ["sh", "-c", "java ${JVM_OPTS}" ]
