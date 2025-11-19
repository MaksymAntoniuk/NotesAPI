package io.maksym.web.requests.library;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static io.maksym.web.config.ApiEndpoints.BASE_URL;
import static io.restassured.RestAssured.given;

public class RequestLibrary {
    private static RequestSpecification getRequestSpec() {
        return given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON);
    }

    public static Response getCheckHealth(String url) {
        return getRequestSpec()
                .when()
                .get(BASE_URL + url).then().log().all().extract().response();
    }
    public static Response postRequest(String url, Record record){
        return getRequestSpec()
                .body(record)
                .when()
                .post(BASE_URL + url).then().log().all().extract().response();
    }
    public static Response postRequest(String url, String toekn, Record record){
        return getRequestSpec()
                .header("accept", "application/json")
                .header("x-auth-token", toekn)
                .body(record)
                .when()
                .post(BASE_URL + url).then().log().all().extract().response();
    }

    public static Response getRequest(String url, String token){
        return given()
                .header("accept", "application/json")
                .header("x-auth-token", token)
                .when()
                .get(BASE_URL + url)
                .then()
                .log().all()
                .extract().response();
    }
    public static Response patchRequest(String url, String token, Record record){
        String jsonBody = null;
        try{
            jsonBody = new ObjectMapper().writeValueAsString(record);
            System.out.println(jsonBody);

        }catch(JsonProcessingException e){
            System.out.println("Error while converting object to json");
            e.printStackTrace();
        }

        return given()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("x-auth-token", token)
                .body(jsonBody)
                .when()
                .patch(BASE_URL + url)
                .then()
                .log().all()
                .extract().response();

    }
    public static Response deleteRequest(String url, String token){
        return getRequestSpec()
                .header("x-auth-token", token)
                .when()
                .delete(BASE_URL + url)
                .then().log().all().extract().response();
    }
    public static Response putRequest(String url, String token, Record record){
        String jsonBody = null;
        try{
            jsonBody = new ObjectMapper().writeValueAsString(record);
            System.out.println(jsonBody);

        }catch(JsonProcessingException e){
            System.out.println("Error while converting object to json");
            e.printStackTrace();
        }

        return given()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("x-auth-token", token)
                .body(jsonBody)
                .when()
                .put(BASE_URL + url)
                .then()
                .log().all()
                .extract().response();

    }
}
