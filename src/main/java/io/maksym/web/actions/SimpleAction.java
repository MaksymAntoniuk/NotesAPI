package io.maksym.web.actions;
import io.maksym.web.library.RequestLibrary;
import io.restassured.response.Response;

public class SimpleAction extends RequestLibrary {
    public Response getCheckHealth(String url){
        return getCheckH(url);
    };
    public Response postRequest(String url, Record record){
        return postReq(url, record);
    };
    public Response getRequest(String url, String token){
        return getReq(url, token);
    };
    public Response patchRequest(String url, String token, Record record){
        return patchReq(url, token, record);
    };
    public Response deleteRequest(String url, String token){
        return deleteReq(url, token);
    };
}
