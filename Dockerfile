# build
FROM maven:3.8.8-amazoncorretto-21-al2023 as build

#dentro da imagem irei construir um diretorio
WORKDIR /build

#copiar o conteudo do meu projeto para dentro do diretorio build
#todos os comando depois do copy serao executados dentro do diretorio build
COPY . .

#comando para construir o projeto
RUN mvn clean package -DskipTests

#rodar a aplicacao
FROM amazoncorretto:21.0.5
WORKDIR /app

COPY --from=build /build/target/*.jar ./libraryapi.jar

EXPOSE 8080
EXPOSE 9090

#DEFINIR VARIAVEL DE AMBIENTE
#ENV DATASOURCE_URL=''
ENV DATASOURCE_USERNAME=''
ENV DATASOURCE_PASS=''
ENV CLIENT_ID=''
ENV CLIENT_SECRET=''

ENV SPRING_PROFILES_ACTIVE='production'

#COLOCAR A VARIAVEL DE AMBIENTE DE FUSO HORARIO
ENV TZ='America/Sao_Paulo'

#INICIAR A APLICACAO
ENTRYPOINT ["java","-jar","libraryapi.jar"]