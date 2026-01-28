# Java Backend API

## Overview

This project is a Java-based backend API designed following clean and production-ready practices. It exposes two main endpoints to manage **Person** data and is fully containerized and deployed on AWS infrastructure with security, scalability, and best practices in mind.

The solution covers API development, database persistence, containerization, cloud deployment, authentication, and secure configuration management.

---

## Features

* REST API with 2 endpoints:

  * **savePerson**: Persist a person in the database
  * **searchPerson**: Retrieve a person by identification number
* Person attributes:

  * Identification number
  * Name
  * Email
* Database persistence using AWS RDS
* Secure authentication using AWS Cognito
* Deployed and scalable using AWS ECS + Load Balancer
* API exposed via AWS API Gateway
* Secure configuration using AWS Parameter Store and Secrets Manager

---

## API Endpoints

### Save Person

```
POST https://4craag3qgd.execute-api.us-east-1.amazonaws.com/prod/api/person
```

**Request Body**

```json
{
  "identificationNumber": "123456789",
  "name": "John Doe",
  "email": "john.doe@email.com"
}
```

### Search Person

```
GET https://4craag3qgd.execute-api.us-east-1.amazonaws.com/prod/api/person/{identificationNumber}
```

---

## Database

* Publicly accessible database (network-level access controlled)
* Hosted on **AWS RDS**
* Dedicated:

  * Database user
  * Schema for the microservice
* All API endpoints persist and retrieve data from this database

 ``` 
 mysql -h person-db-1.capcy8qs629e.us-east-1.rds.amazonaws.com -P 3306 -u admin -p
 ```
 ```
 source scripts/init-db.sql 
 ```
---

## Docker & Deployment

### Containerization

* Dockerfile configured to build the API image
* Image built and published to **Amazon ECR**

### AWS Infrastructure

* **ECS** provisioned to run the containerized API
* **Application Load Balancer** configured for traffic distribution
* API Gateway configured to expose endpoints

Pre deployment flow:
* create repository in ECR
  ``` aws ecr create-repository --repository-name person-api --region us-east-1  ```

* login inside ECR
 ``` aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin 847143401195.dkr.ecr.us-east-1.amazonaws.com/person-api  ```

Deployment flow:

1. Build Docker image: Development environment -> docker build -t person-api .
2. Push image to Amazon ECR: Development environment -> docker push 847143401195.dkr.ecr.us-east-1.amazonaws.com/person-api:latest
3. Provision ECS cluster and service: AWS Console -> Create cluster (fargate, Task role, Task execution role, etc.) -> Create Task Definition instructions -> Create Service according Task Definition associated
4. Deploy API container from ECR to ECS: AWS Console -> Fargate cluster, Task definition, Security group, Load balancing
5. Configure API Gateway routes: AWS Console -> Configure user pool in cognito -> AWS Gateway routes definitions with authorization according user pool created.
6. Validate persistence to RDS: AWS Console -> Test endpoint one ECS tasks is ready
7. Enable Load Balancer for high availability -> AWS Console -> configuration (name, subnets, security group), Target Group for routes available and healthy endpoint.

---

## Security & Authentication

### AWS Cognito

* Cognito configured as authentication service:  AWS Console -> Configure user pool in cognito (Sign-in option: email, Authentication: ALLOW_USER_PASSWORD_AUTH, no generate client secret)
* register user
```
aws cognito-idp admin-create-user \
    --user-pool-id us-east-1_RYZ2TGnSh \
    --username dada@email.com \
    --temporary-password "D12344321a!" \
    --user-attributes Name=email,Value=dada@email.com Name=email_verified,Value=true
```
* set permanent password
```
aws cognito-idp admin-set-user-password \
    --user-pool-id us-east-1_RYZ2TGnSh \
    --username dada@email.com \
    --password "D12344321a!" \
    --permanent
 ```
* API Gateway integrated with Cognito: AWS Console -> Add cognito as authorizer
* Endpoints cannot be accessed without a valid token: AWS Console -> Assing cognito user pool token source authorization

---

## Configuration Management

### Environment Variables

* No hardcoded ("burned") variables in the codebase
* All sensitive and environment-specific values managed using:

  * **AWS Parameter Store**
  ```
    aws ssm put-parameter \
    --name "/person-api/db-host" \
    --value "<RDS_ENDPOINT>" \
    --type String
  ```
  ```
  aws ssm put-parameter \
        --name "/person-api/db-port" \
        --value "3306" \
        --type String
  ```
  ```
  aws ssm put-parameter \
        --name "/person-api/db-name" \
        --value "person_db" \
        --type String
  ```
  * **AWS Secrets Manager**
  ```
   aws secretsmanager create-secret \
    --name "/persona-api/db-credentials" \
    --secret-string '{"username":"admin","password":"PASSWORD_RDS"}'
  ```
### Integration

* ECS integrated with Parameter Store and Secrets Manager
  ```
  DB_HOST      -> ValueFrom: arn:aws:ssm:us-east-1:847143401195:parameter/person-api/db-host
  DB_PORT      -> ValueFrom: arn:aws:ssm:us-east-1:847143401195:parameter/person-api/db-port  
  DB_NAME      -> ValueFrom: arn:aws:ssm:us-east-1:847143401195:parameter/person-api/db-name
  DB_USERNAME  -> ValueFrom: arn:aws:secretsmanager:us-east-1:847143401195:secret:/person-api/db-credentials:username::
  DB_PASSWORD  -> ValueFrom: arn:aws:secretsmanager:us-east-1:847143401195:secret:/person-api/db-credentials:password::
  ```
* API dynamically consumes configuration at runtime: According to 'execution role' defined in Task Definition step
* Application updated to use externalized configuration

---

## Validation

* API endpoints validated with and without authentication
* Verified:

  * Unauthorized access is blocked
  * Authorized access works correctly
  * Data persistence to RDS is successful

---

## Technology Stack

* Java
* Spring Boot (REST API)
* Maven
* Docker
* AWS ECR
* AWS ECS
* AWS RDS
* AWS API Gateway
* AWS Cognito
* AWS Parameter Store & Secrets Manager
* Load Balancer

---

## Quality & Best Practices

* SonarLint integrated for static code analysis
* Clean architecture principles
* Secure secret handling
* Cloud-native and scalable design


---

## Author
* Cuaichar
  
---
Developed as a cloud-ready Java backend microservice with enterprise-grade practices.
