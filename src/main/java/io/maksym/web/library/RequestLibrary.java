package io.maksym.web.library;

import io.maksym.web.dto.HealthCheck.HealthCheckResponse;
import io.maksym.web.dto.Login.LoginResponse;
import io.maksym.web.dto.Profile.ProfileResponse;
import io.maksym.web.dto.Registration.RegistrationSuccResponse.RegistrationSuccessfulResponse;
import io.maksym.web.dto.User.User;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;

import static io.maksym.web.config.ApiEndpoints.*;
import static io.maksym.web.config.ApiEndpoints.BASE_URL;
import static io.maksym.web.config.ApiEndpoints.ENDPOINT_LOG_IN;
import static io.restassured.RestAssured.given;

public interface RequestLibrary {
    private static RequestSpecification getRequestSpec() {
        return given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON);
    }

    default HealthCheckResponse getCheckHealth() {
        return getRequestSpec()
                .when()
                .get(BASE_URL + ENDPOINT_HEALTH_CHECK).then().log().all().extract().as(HealthCheckResponse.class);
    }

    default RegistrationSuccessfulResponse postCreateUser(User user){
        return getRequestSpec()
                .body(user)
                .when()
                .post(BASE_URL + ENDPOINT_CREATE_USER).then().log().all().extract().as(RegistrationSuccessfulResponse.class);
    }

    default LoginResponse postLogIn(String email, String password){
        return getRequestSpec()
                .body(new HashMap<String, String>() {{ put("email", email); put("password", password);}} )
                .when()
                .post(BASE_URL + ENDPOINT_LOG_IN).then().log().all().extract().as(LoginResponse.class);
    }
    default ProfileResponse getUserProfile(String token){
        return given()
                .header("accept", "application/json")
                .header("x-auth-token", token)
                .when()
                .get(BASE_URL + ENDPOINT_GET_USER_PROFILE)
                .then()
                .log().all()
                .extract().as(ProfileResponse.class);
    }
    default ProfileResponse updateUserProfileName(String token, String name){
        return given()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("x-auth-token", token)
                .body(new HashMap<String, String>() {{ put("name", name); }})
                .when()
                .patch(BASE_URL + ENDPOINT_UPDATE_USER_PROFILE)
                .then()
                .log().all()
                .extract().as(ProfileResponse.class);

    }
    default ProfileResponse updateUserProfileCompany(String token,  String name, String company){
        return given()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("x-auth-token", token)
                .body(new HashMap<String, String>() {{ put("name", name); put("company", company); }})
                .when()
                .patch(BASE_URL + ENDPOINT_UPDATE_USER_PROFILE)
                .then()
                .log().all()
                .extract().as(ProfileResponse.class);

    }
    default ProfileResponse updateUserProfilePhone(String token, String name, String phone){
        return given()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("x-auth-token", token)
                .body(new HashMap<String, String>() {{ put("phone", phone); put("name", name);}})
                .when()
                .patch(BASE_URL + ENDPOINT_UPDATE_USER_PROFILE)
                .then()
                .log().all()
                .extract().as(ProfileResponse.class);
    }
    default ProfileResponse updateUserProfile(String token, String name, String phone, String company){
        return given()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("x-auth-token", token)
                .body(new HashMap<String, String>() {{ put("name", name); put("company", company); put("phone", phone);}})
                .when()
                .patch(BASE_URL + ENDPOINT_UPDATE_USER_PROFILE)
                .then()
                .log().all()
                .extract().as(ProfileResponse.class);
    }
    default HealthCheckResponse deleteUser(String token){
        return getRequestSpec()
                .header("x-auth-token", token)
                .when()
                .delete(ENDPOINT_DELETE_USER_PROFILE)
                .then().log().all().extract().as(HealthCheckResponse.class);

    }

}
