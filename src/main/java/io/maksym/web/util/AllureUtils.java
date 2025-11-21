package io.maksym.web.util;

import io.qameta.allure.Allure;
import io.restassured.response.Response;

public class AllureUtils {
    public static void attachResponseToAllure(Response response){
        String body = response.getBody().asPrettyString();
        int statusCode = response.getStatusCode();
        String contentType = response.getHeader("Content-Type");
        if(contentType == null){
            contentType = "application/json";
        }
        Allure.addAttachment("Response_log_status_" + statusCode + ".json", contentType, body);
    }
}
