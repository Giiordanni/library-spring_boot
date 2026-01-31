# library-spring_boot

Este repositório contém uma API de biblioteca (library-api) desenvolvida como projeto de estudo para aprender e praticar Spring Boot.

## Objetivo

O propósito principal deste projeto é estudo e experimentação. Foi criado para servir como referência pessoal, consulta e para aprofundar conhecimentos sobre Spring Boot, práticas de desenvolvimento e organização de uma API REST.

Por esse motivo você encontrará muitos trechos de código comentados, descrições de funções e várias anotações no código — tudo isso faz parte do processo de aprendizado e foi deixado intencionalmente para facilitar a revisão e o entendimento.

## Conteúdo

- Implementações de controllers, services e repositories típicos de uma API REST.
- DTOs (data transfer objects) e validações.
- Tratamento de exceções e respostas padronizadas.
- Testes unitários e de integração (quando presentes).
- Spring Security
- OAuth2 com JWT

## Como rodar

Pré-requisitos: Java 21+ e Maven (ou use o wrapper incluído).

Executar em desenvolvimento com o wrapper Maven:

./mvnw spring-boot:run

Ou construir e executar o JAR:

./mvnw clean package
java -jar target/libraryapi-0.0.1-SNAPSHOT.jar

## Uso e contribuições

Este código está aberto para todos que queiram testar, estudar e aprender com ele. Sinta-se à vontade para clonar o repositório, experimentar, abrir issues com dúvidas ou sugestões e enviar pull requests.

Nota: por se tratar de um projeto didático, nem tudo está pronto para produção e pode conter simplificações ou decisões feitas exclusivamente para aprendizado.

## Observações finais

Aprender é bom, compartilhar é ainda melhor
