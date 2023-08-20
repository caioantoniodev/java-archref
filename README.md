<br/>

[![Spring](https://img.shields.io/badge/-Spring-%236DB33F?logo=Spring&logoColor=%23FFF)](https://spring.io/)
[![Redis](https://img.shields.io/badge/-Redis-%23DC382D?logo=Redis&logoColor=%23FFF)](https://redis.io/)
[![Kibana](https://img.shields.io/badge/-MongoDB-%2347A248?logo=MongoDB&logoColor=%23FFF)](https://www.elastic.co/pt/)
[![RabbitMQ](https://img.shields.io/badge/-RabbitMQ-%23FF6600?logo=RabbitMQ&logoColor=%23FFF)](https://www.rabbitmq.com/)
[![Swagger](https://img.shields.io/badge/-Swagger-%2385EA2D?logo=Swagger&logoColor=%23000)](https://swagger.io/)
[![Docker](https://img.shields.io/badge/-Docker-%232496ED?logo=Docker&logoColor=%23FFF)](https://swagger.io/)

<br/>

# ☁️ Arch-Ref Spring

Esse projeto é um template de arquitetura de referência para projetos em Java 17 com spring boot e maven

<br/>

## 📚 Resources

Esse microserviço apresenta o recurso de personagem (character) na qual haja um gerencialmente desse recursos como CRUD

<br/>

## ✔️ Character

<kbd>/character</kbd>

Recurso que representa um personagem qualquer como um **entidade**

| METHOD     | ENDPOINT         | DESCRIPTION                               | ESCOPE             |
|------------|------------------|-------------------------------------------|--------------------|
| **POST**   | `/`              | Cria um novo personagem                   | <kbd>REQUEST</kbd> |
| **POST**   | `/random`        | Cria um personagem aleatório              | <kbd>REQUEST</kbd> |
| **DELETE** | `/{characterId}` | Remove um personagem específico pelo Id   | <kbd>REQUEST</kbd> |
| **PUT**    | `/{characterId}` | Atualiza um personagem específico pelo Id | <kbd>REQUEST</kbd> |
| **GET**    | `/{characterId}` | Lista um personagem específico pelo Id    | <kbd>REQUEST</kbd> |

<br/> 

## 📐 Arquitetura

Esse microsserviço foi estruturado
usando [arquitetura hexagonal](https://en.wikipedia.org/wiki/Hexagonal_architecture_(software)#:~:text=The%20hexagonal%20architecture%2C%20or%20ports,means%20of%20ports%20and%20adapters.)
seguindo a estrutura de pastas abaixo

```
  /src
    /adapters
      /amqp
      /http
        /inbound
    /config
    /domain
      /entities
      /repositories	
        /entity
      /services
    /infrasctructure
      /amqp
        /config
      /database
        /mongo
          /entity
      /http
      /logger
```

<br/>

## ⌛️ Serviços

- ### 🌐 **HTTP**
  Esse microsserviço faz proxy com uma API pública de personagens da séries Marvel

- ### 🟥 **Redis**
  Esse microsserviço armazena e faz leitura de serviço em cache com [Redis](https://redis.io/).

- ### 🍃 **MongoDB**
  Esse microsserviço usa armazenamento com banco de dados não relacional com [MongoDB](https://www.mongodb.com/).

- ### 🟠 **RabbitMQ**
  Esse microsserviço usa implementação de *event broker* com [RabbitMQ](https://www.rabbitmq.com/) com publicadores e
  consumidores da fila.

<br/>

## ⚡ Getting started

### Instalação do archetype localmente

```sh

mvn archetype:create-from-project
cd target/generated-sources/archetype
mvn install
```

### Criação de um projeto a partir do archetype

```sh
mvn archetype:generate
```

### Exemplo de definições do novo projeto

* Choose a number or apply filter: (Na listagem escolha o número correspondente ao projeto de archetype Ex.:3187:
  local -> tech.api.archref:archref-archetype)
* Define value for property 'groupId': com.api
* Define value for property 'artifactId': empresa
* Define value for property 'version' 1.0-SNAPSHOT:
* Define value for property 'package' com.api:

<br/>

## ⚡ Getting started

Executa o docker compose para subir as imagens necessárias em container docker
=======
cp .env.sample .env

```

Executa o docker compose para subir as imagens necessárias em container docker

```sh
cd docker && docker-compose up -d
```

<br/>

## ☕ Executar

### Executando **local**

```
yarn start:dev
```

> > > > > > > main

### Executando os **testes**

```sh
docker-compose up -d
```

<br/>

## ☕ Executar

### Compilar o projeto

```
mvn clean install
```[README.md](README.md)

### Executando **local**
```

docker build -t springarchref:master .
docker run -d -p 8080:8080 --name arc springarchref:master

```

### Executando os **testes**
```sh
mvn test

yarn test

newman run backend-archref-collection.json -e backend-archref-dev-environment.json
```

### Executando a **cobertura**

```
yarn test:cov
```

### **Swagger**

> > > > > > > main

```
http://localhost:{you-port}/v1/api-docs
```

<br/>

## 😄 Contribua

Quer fazer parte desse projeto? Clique [AQUI](https://bitbucket.org/sensedia/archref-java/pull-requests/) e abra
um `pull request` 🧩
<br/>
