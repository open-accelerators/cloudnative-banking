package com.redhat.bankdemo;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.containsString;

@QuarkusTest
public class CustomerResourceTest {

    @Test
    public void testCustomersEndpoint() {
        given()
          .when().get("/services/customers")
          .then()
             .statusCode(200)
             .body(containsString("\"city\":\"Raleigh\""));
    }

}