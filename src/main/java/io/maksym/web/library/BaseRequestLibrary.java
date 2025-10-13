package io.maksym.web.library;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class BaseRequestLibrary {
    public static RequestSpecification getRequestSpec() {
        return given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .log().all();
    }
}
