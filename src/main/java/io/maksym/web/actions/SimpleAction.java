package io.maksym.web.actions;

import io.maksym.web.dto.HealthCheck.HealthCheckResponse;
import io.maksym.web.dto.Login.LoginResponse;
import io.maksym.web.dto.Registration.RegistrationSuccResponse.RegistrationSuccessfulResponse;
import io.maksym.web.dto.User.User;
import io.restassured.http.ContentType;

import java.util.HashMap;

import static io.maksym.web.config.ApiEndpoints.*;
import static io.maksym.web.library.BaseRequestLibrary.getRequestSpec;
import static io.restassured.RestAssured.given;


public class SimpleAction {


    public HealthCheckResponse checkHealth() {
        return getRequestSpec()
                .when()
                .get(BASE_URL + ENDPOINT_HEALTH_CHECK)
                .then().statusCode(200).extract().as(HealthCheckResponse.class);
    }

    public RegistrationSuccessfulResponse createUser(User user){
        return getRequestSpec()
                .body(user)
                .when()
                .post(BASE_URL + ENDPOINT_CREATE_USER)
                .then()
                .extract().as(RegistrationSuccessfulResponse.class);
    }

    public LoginResponse logIn(String email, String password){
        return getRequestSpec()
                .body(new HashMap<String, String>() {{ put("email", email); put("password", password);}} )
                .when()
                .post(BASE_URL + ENDPOINT_LOG_IN)
                .then()
                .extract().as(LoginResponse.class);
    }

}
