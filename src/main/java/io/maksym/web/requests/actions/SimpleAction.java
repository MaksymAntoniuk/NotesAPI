package io.maksym.web.actions;
import io.restassured.response.Response;

import static io.maksym.web.config.ApiEndpoints.*;
import static io.maksym.web.library.RequestLibrary.*;

public  interface SimpleAction {
    default Response checkHealthOfApiService(){
        return getCheckHealth(ENDPOINT_HEALTH_CHECK);
    };
    default Response getUserProfileData(String token){
        return getRequest(ENDPOINT_GET_USER_PROFILE, token);
    };
    default Response updateUser(String token, Record record){
        return patchRequest(ENDPOINT_UPDATE_USER_PROFILE, token, record);
    };
    default Response deleteUserProfile(String token){
        return deleteRequest(ENDPOINT_DELETE_USER_PROFILE, token);
    };
    default Response registerUser(Record record){
        return postRequest(ENDPOINT_CREATE_USER, record);
    }
    static Response logInUser(Record record){
        return postRequest(ENDPOINT_LOG_IN, record);
    }
    static Response createNote(String token, Record record){
        return postRequest(ENDPOINT_CREATE_NOTE, token, record);
    }
}
