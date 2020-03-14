package de.harald.testcontainers;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasItem;

import java.io.File;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import io.restassured.RestAssured;

@Testcontainers
public class GreetingResourceIT {

    private static final String APP_SERVICENAME = "app_1";
    private static final int APP_PORT = 8080;

    @Container
    private static final DockerComposeContainer<?> environment =
        new DockerComposeContainer<>(new File("docker-compose.yml"))
            .withExposedService(APP_SERVICENAME, APP_PORT, Wait.forHttp("/health"))
            .withTailChildContainers(false)
            .withBuild(true);

    @BeforeAll
    public static void setupRestAssured() {
        RestAssured.baseURI = "http://" + environment.getServiceHost(APP_SERVICENAME, APP_PORT);
        RestAssured.port = environment.getServicePort(APP_SERVICENAME, APP_PORT);
    }
    
    @Test
    public void testDatabaseEndpoint() {
        given()
          .when().get("/hello/db")
          .then()
             .statusCode(200)
             .body("id", is(1))
             .body("message", is("hello"));
    }

    @Test
    public void testKafkaEndpoint() {
        given()
            .when().get("/hello/kafka")
            .then()
            .statusCode(200)
            .body("$", hasItem("world"));
    }

}