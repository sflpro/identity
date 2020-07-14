[![Build Status](https://travis-ci.org/sflpro/identity.svg?branch=master)](https://travis-ci.org/sflpro/identity)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=com.sflpro.identity%3Aidentity&metric=alert_status)](https://sonarcloud.io/dashboard?id=com.sflpro.identity%3Aidentity)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.sflpro.identity/identity/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.sflpro.identity/identity/)
[![License: APACHE](https://img.shields.io/badge/license-Apache%20License%202.0-b)](https://opensource.org/licenses/Apache-2.0)


# Identity Microservice
The identity microservice is an independently deployable component intended for managing various types of principals for identities.

## Supported principal types
Identity supports the most common principal types. 
* Mail
* Phone
* User name
* Token

## Integration

You can call the identity's REST API directly or use the provided Java client library. To use the client library add the 
following maven dependency:
```xml
<dependency>
    <groupId>com.sflpro.identity</groupId>
    <artifactId>identity-api-client</artifactId>
    <version>0.1.0</version>
</dependency>
```

The API is documented using OpenAPI Specification(Swagger) and the documentation JSON is available under `/swagger.json` 
once the identity microservice has been started. 

## Deployment

Identity is dockerized and is easy to deploy as a docker container. For more details, see the images on docker hub:  
https://hub.docker.com/r/sflpro/identity-service

## Running the application locally

#### 1. Configure POSTGRES datasource
The postgres has official images in [docker hub](https://hub.docker.com/_/postgres).
```bash
docker run --name identity-postgres -e POSTGRES_PASSWORD=identity -e POSTGRES_USER=identity -e POSTGRES_DB=identity -p 5432:5432 -d postgres:11
```

#### 2. Prepare properties file for identity images
```properties
# Postgres
spring.datasource.url=jdbc:postgresql://{HOST_IP}:5432/POSTGRES_DB
spring.datasource.username=POSTGRES_USER
spring.datasource.password=POSTGRES_PASSWORD

# Container port
server.port=8080
```

#### 3. Run identity-api
Running [Identity-API](https://hub.docker.com/r/sflpro/identity-service) docker images.
```bash
docker run -p 8080:8080 -v {IDENTITY_-_PROPERTIES_PATH}:/etc/identity/identity.properties sflpro/identity-api:0.1.0 --spring.config.location=etc/identity/identity.properties
```

#### 4. Testing setup
```curl
curl -X GET "https://localhost:8080/status" -H "accept: application/json"
```

#### 5. Release new version
```mvn
mvn gitflow:release -DgpgSignTag=true -DgpgSignCommit=true
```