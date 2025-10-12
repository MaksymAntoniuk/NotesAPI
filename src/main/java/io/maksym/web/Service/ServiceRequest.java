package io.maksym.web;

import io.maksym.web.DTO.HealthCheck.HealthCheckResponse;
import io.maksym.web.DTO.Registration.RegistrationSuccResponse.RegistrationSuccessfulResponse;
import io.maksym.web.DTO.User.User;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;


public class ServiceRequest {
    String BASE_URL = "https://practice.expandtesting.com/notes/api/";
    String ENDPOINT_CREATE_USER = "users/register";
    String ENDPOINT_HEALTH_CHECK = "health-check";

    HealthCheckResponse checkHealth() {
        HealthCheckResponse response = given().when().get(BASE_URL + ENDPOINT_HEALTH_CHECK).then().statusCode(200).extract().as(HealthCheckResponse.class);
        return response;
    }

    RegistrationSuccessfulResponse createUser(User user){
        RegistrationSuccessfulResponse response = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(user)
                .when()
                .post(BASE_URL + ENDPOINT_CREATE_USER)
                .then()
                .log().all()
                .extract().as(RegistrationSuccessfulResponse.class);
        return response;
    }

}
