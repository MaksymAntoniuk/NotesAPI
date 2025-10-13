package io.maksym.web.actions;

import io.maksym.web.dto.HealthCheck.HealthCheckResponse;
import io.maksym.web.dto.Login.LoginResponse;
import io.maksym.web.dto.Registration.RegistrationSuccResponse.RegistrationSuccessfulResponse;
import io.maksym.web.dto.User.User;
import io.restassured.http.ContentType;

import java.util.HashMap;

import static io.maksym.web.config.ApiEndpoints.*;
import static io.restassured.RestAssured.given;


public class SimpleAction {
    
    public HealthCheckResponse checkHealth() {
        return given().when().get(BASE_URL + ENDPOINT_HEALTH_CHECK).then().statusCode(200).extract().as(HealthCheckResponse.class);
    }

    public RegistrationSuccessfulResponse createUser(User user){
        return given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(user)
                .when()
                .post(BASE_URL + ENDPOINT_CREATE_USER)
                .then()
                .log().all()
                .extract().as(RegistrationSuccessfulResponse.class);
    }

    public LoginResponse LogInWithUser(String email, String password){
        return given()
        .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(new HashMap<String, String>() {{ put("email", email); put("password", password);}} )
                .when()
                .post(BASE_URL + ENDPOINT_LOG_IN)
                .then()
                .log().all()
                .extract().as(LoginResponse.class);
    }

}
