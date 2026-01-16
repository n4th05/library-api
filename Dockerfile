# 1- build
FROM maven:3.8.8-amazoncorretto-21-al2023 as build

#criando uma pasta chamada build:
WORKDIR /build

# Pegando o código fonte (LIBRARY-API) e copiando para a pasta build:
COPY . . 


RUN mvn clean package -DskipTests


# 2- run
FROM amazoncorretto:21.0.5
WORKDIR /app

COPY --from=build ./build/target/*.jar ./libraryapi.jar

# Expondo as portas 8080 e 9090
EXPOSE 8080
EXPOSE 9090

ENV DATASOURCE_URL=''
ENV DATASOURCE_USERNAME=''
ENV DATASOURCE_PASSWORD=''
ENV GOOGLE_CLIENT_ID='client_id'
ENV GOOGLE_CLIENT_SECRET='client_id'

ENV SPRING_PROFILES_ACTIVE='production'
ENV TZ='America/Sao_Paulo'

ENTRYPOINT java -jar libraryapi.jar