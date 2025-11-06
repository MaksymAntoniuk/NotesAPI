package io.maksym.web.actions;
import io.restassured.response.Response;

import static io.maksym.web.library.RequestLibrary.*;

public  interface SimpleAction {
    default Response getCheckHealth(String url){
        return getCheckH(url);
    };
    default Response postRequest(String url, Record record){
        return postReq(url, record);
    };
    default Response getRequest(String url, String token){
        return getReq(url, token);
    };
    default Response patchRequest(String url, String token, Record record){
        return patchReq(url, token, record);
    };
    default Response deleteRequest(String url, String token){
        return deleteReq(url, token);
    };
}
