FROM maven:3.9.6-eclipse-temurin-21-alpine AS BUILD

COPY . /app
WORKDIR /app

RUN mvn -e -X --batch-mode -f pom.xml clean compile package install -Dmaven.test.skip=true
RUN cp  /app/target/*.jar /app/target/app.jar

FROM eclipse-temurin:21-jdk-alpine

ENV PORT 8080
EXPOSE 15000

RUN  apk update && apk upgrade
COPY --from=BUILD /app/target/app.jar /opt/target/

WORKDIR /opt/target

# Crear usuario
RUN adduser -D dockeruser

# Asignar permisos
RUN chown -R dockeruser /opt/target/

# Se define el usuario a ejecutar
USER dockeruser

# Descomentar la linea de abajo para ejecutar sin apm
CMD ["/bin/sh", "-c", "java -jar app.jar" ]

