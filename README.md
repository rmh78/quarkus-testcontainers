# Quarkus - Integration Tests

## (1) Background 

This Quarkus project uses [Testcontainers](https://www.testcontainers.org) with the [Docker Compose Module](https://www.testcontainers.org/modules/docker_compose/) for setting up an **integration-test environment** which contains:

* Postgres DB
* Kafka Broker
* Kafka Zookeeper
* Quarkus application

I'm using Mavens **integration-test** and **verify** phase to run my integration-tests against the docker-compose environment started by testcontainers. The test-classes are normal JUnit5 tests without the Quarkus annotations ``@QuarkusTest`` and ``@NativeImageTest``. 

This mechanism addresses an integration-test scenario where all real world components (postgres, kafka, etc. but no mocks or H2 dbs) and the application itself are in place for being tested from outside via REST calls. Without the need to deploy the whole stuff to your target cloud environment.

## (2) run locally

### (2.1) run integration-tests in JVM mode

1. Change inside docker-compose.yml the ``dockerfile`` property of the app service to ``Dockerfile.jvm`` and use the jvm command

2. Maven verfiy

    ```bash
    mvn verify
    ```

### (2.2) run integration-tests in native mode

1. Change inside docker-compose.yml the ``dockerfile`` property of the app service to ``Dockerfile.native`` and use the native command

2. Maven verfiy

    ```bash
    mvn verify -Pnative
    ```

## (3) run on Jenkins

``mvn verify`` can be used at any CI build-pipeline too.

## (4) other stuff

* I'm using the shell-script ``wait-for-it.sh`` (https://github.com/jlordiales/wait-for-it) to wait with the Quarkus application start until all dependent services are up and running.