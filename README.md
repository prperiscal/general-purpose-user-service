[![Codacy Badge](https://api.codacy.com/project/badge/Grade/89f81045a7da4e259283a769d94c95bb)](https://www.codacy.com/app/prperiscal/general-purpose-user-service?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=prperiscal/general-purpose-user-service&amp;utm_campaign=Badge_Grade)
# general-purpose-environment

General purpose micro-services and architecture provide an extended and robust base from where a new project
can be started and developed following a consolidated agile approach and with the most used and assumed technologies in the 
micro-service spring development ecosystem.

Take a look on [General Purpose Environment](https://gist.github.com/prperiscal/900729941edc5d5ddaaf9e21e5055a62) to get more information on how everything integrates together and how the agile development should work.

# general-purpose-user-service

Micro-service to manage user data. Allows user grouping and multi-tenant functionality.

If license management is not included at the moment, so no remote dependencies are need. This micro-services does not 
communicate directly (as source) with others. 
Although it publish actions on user create and delete through kafka to notify any service who need to be aware of user changes.

## Contributing

Please read [CONTRIBUTING](https://gist.github.com/prperiscal/900729941edc5d5ddaaf9e21e5055a62) for details on our code of conduct, and the process for submitting pull requests to us.

## Workflow

Please read [WORKFLOW-BRANCHING](https://gist.github.com/prperiscal/ce8b8b5a9e0f79378475243e2d227011) for details on our workflow and branching directives. 

## Technologies involved

* [Spring 5.0.1.RELEASE](https://spring.io/) / [Spring boot 2.0.0.RELEASE](https://projects.spring.io/spring-boot/)
* [__Spring Data JPA__](http://projects.spring.io/spring-data/) Deals with enhanced support for JPA based data access layers.
* [__Hibernate__](http://hibernate.org/) Object/Relational Mapping (ORM) framework.
* [__Flyway__](https://flywaydb.org/) Version control for the database.
* [__Spring Projection Resolver__](https://github.com/prperiscal/spring-projection-resolver) Custom spring module to resolve projections.
* [__Spring Data Compose__](https://github.com/prperiscal/spring-data-compose) Custom spring module to load data into DB from json, within tests.
* [__TestContainers__](https://www.testcontainers.org/) Supports JUnit tests with common databases that can run in a Docker container.
* [Spring Cloud](https://cloud.spring.io/spring-cloud-netflix/)
  * [__Hystrix__](https://github.com/Netflix/Hystrix/blob/master/README.md) Latency and fault tolerance support on points of access.
  * [__Eureka Client__](https://github.com/Netflix/eureka/blob/master/README.md) For announcing the service in the eureka server and make it visible.
  * [__Ribbon__](https://github.com/Netflix/ribbon/blob/master/README.md) Load balancer.

## Getting Started

Get you a copy of the project up and running on your local machine for development and testing purposes with:
```
git clone https://github.com/prperiscal/general-purpose-user-service
```
Also, this service can be started with docker:
```
docker pull quay.io/prperiscal/general-purpose-user-service:1.0.0-SNAPSHOT
```
Change the version as desire.

### Prerequisites

This services makes uses of a postgres database, which by default should be accessible on localhost:5432

Eureka server must be running too.

## Internals

Here is explained what is the User server and how is it configured.

[TODO]

## Authors

* **Pablo Rey Periscal** - *Initial work* -

See also the list of [contributors]() who participated in this project.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details
