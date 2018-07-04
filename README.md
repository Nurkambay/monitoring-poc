# Monitoring Proof Of Concept


## Architecture overview
Monitoring System is designed to monitor vehicle statuses

Vehicle sends status to Monitoring System and end user can monitor vehicle statuses.

![Monitoring System Design](https://docs.google.com/drawings/d/e/2PACX-1vSpZRgcqXSRAcG2e3ZTaddAd-HuCAo1s6Em1siCE1YyHnhmmqmZ7E55Dm2NwYxAnnmS-5Xnl2WFGziX/pub?w=927&h=432)


### Monitoring System must be:

1. High availability for vehicle request
2. Even if some parts of a system down Vehicle Request must be received
3. Update for Monitoring System Site should not affect Vehicle requests processing

In that case Monitoring System should have Microservice Architecture with separated microservices.

### Infrastructure
For the infrastructure was chosen Amazon Web Services with wide abilities to setup, monitor and scale web applications.

### Platform
For AWS applications one of the best platforms is Java 8 platform.

### Framework
Java Spring Boot provides ORM, Test frameworks, dependency injections, etc. 


### Microservices
1. Monitoring Receiver (monitoring-receiver module)
    Receives Vehicle Requests VIA REST Api and send payload to AWS SQS queue
    
2. Monitoring Service (monitoring-service module)
    Receives AWS SQS messages payload and process them by updating Vehicles.
    Results are storing to Database currently based on AWS RDS for high availability
    
3. Monitoring Site (monitoring-site module)
    Monitoring Site is one page site with list of vehicles
    

### Pluses of current architecture
1. Each of microservices can be updated and re-deployed independently.
2. Re-deployment of Monitoring Site does not affect to Vehicle request processing
3. re-deployment of Monitoring Service do not stop receiving Vehicle requests
4. Monitoring Receiver is light microservice that not related to Database and can be easily scaled for high availibility.

## Dockerization
1. Monitoring Receiver (monitoring-receiver module)
Exposed port: 5000 
[Docker file](https://github.com/Nurkambay/monitoring-poc/blob/master/monitoring-receiver/Dockerfile)

2. Monitoring Service (monitoring-service module)
No exposed port 
[Docker file](https://github.com/Nurkambay/monitoring-poc/blob/master/monitoring-service/Dockerfile)

3. Monitoring Site (monitoring-service module)
Exposed port: 5000 
[Docker file](https://github.com/Nurkambay/monitoring-poc/blob/master/monitoring-site/Dockerfile)
    
## Cloud deployment
Current solution can be deployed on AWS by two ways:

1. Using Elastic Beanstalk
    Elastic Beanstalk allows to deploy Java 8 web applications with or without dockerization and setup Load Balancer for high perfomance and availability.
    
2. Cluster Management Service
    
Database can be easily setted up on Amazon RDS.
Liquibase scripts for initial database:

## Deployment steps
1. Create Amazon SQS
2. Setup Amazon RDS database (Aurora or MySQL)
4. Modify Application properties (sqs url, aws credentials and datasource settings):

4.1. [Monitoring Receiver property file](https://github.com/Nurkambay/monitoring-poc/blob/master/monitoring-receiver/src/main/resources/application.properties)
```
queue.url=https://sqs.us-west-1.amazonaws.com/843885655249/eventbus

aws.clientRegion=us-west-1
aws.accessKey=<access key>
aws.secretKey=<secret key>

```
4.2. [Monitoring Service property file](https://github.com/Nurkambay/monitoring-poc/blob/master/monitoring-service/src/main/resources/application.properties)
```
spring.datasource.url=jdbc:mysql://alten.cxx7veyqfyo7.us-west-1.rds.amazonaws.com:3306/monitoring
spring.datasource.username=root
spring.datasource.password=<pswd>

queue.url=https://sqs.us-west-1.amazonaws.com/843885655249/eventbus
queue.max.messages=10
queue.schedule=5000

aws.clientRegion=us-west-1
aws.accessKey=<access key>
aws.secretKey=<secret key>
```

4.3. [Monitoring Site property file](https://github.com/Nurkambay/monitoring-poc/blob/master/monitoring-site/src/main/resources/application.properties)
```
spring.datasource.url=jdbc:mysql://alten.cxx7veyqfyo7.us-west-1.rds.amazonaws.com:3306/monitoring
spring.datasource.username=root
spring.datasource.password=<pswd>
```

5. Build with maven
    mvn clean install
    
6. Build docker images (optional)

7. Deploy to AWS
    3.1. Elastic Beanstalk (jar or docker image)
    3.2. Cluster Management Service (docker image)

### Future improvements
1. Application properties must be based on environment variables
2. Build docker images (if needed) must be a part of building process

 
## Vehicle simulation
By default is switched on.

To switch off vehicle simulation change following application property:
```
simulator.enabled=true
```
[Application properties fle](https://github.com/Nurkambay/monitoring-poc/blob/master/monitoring-receiver/src/main/resources/application.properties)

## Serverless deployment possibility

Monitoring system microservice applications can be deployed to AWS Lambda.
AWS Lambda provides serverless infrastructure for applications and triggers.

### What should be done
1. Monitoring Receiver (monitoring-receiver module)
    Add Lambda infrastructure integration

2. Monitoring Service (monitoring-service module)
    Can be fully implemented as Lambda trigger and linked to SQS Queue

3. Monitoring Site (monitoring-site module)
    Add Lambda infrastructure integration and setup Amazon API Gateway for Lambda


## Deployed example with switched off Vehicle Simulation
[Monitoring Site](http://monitoringsite-env.fvqaajdqge.us-west-1.elasticbeanstalk.com/index.html)
[Monitoring Receiver](http://receiver.us-west-1.elasticbeanstalk.com/swagger-ui.html)
 