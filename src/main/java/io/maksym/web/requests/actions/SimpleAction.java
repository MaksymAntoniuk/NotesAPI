package io.maksym.web.requests.actions;
import io.restassured.response.Response;

import static io.maksym.web.config.ApiEndpoints.*;
import static io.maksym.web.requests.library.RequestLibrary.*;

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
    default Response getAllNotes(String token){
        return getRequest(ENDPOINT_GET_ALL_NOTES, token);
    }
    default Response getNoteById(String token, String id){
        return getRequest(ENDPOINT_GET_NOTE_BY_ID + id, token);
    }
    default Response deleteNoteById(String token, String id){
        return deleteRequest(ENDPOINT_DELETE_NOTE + id, token);
    }
    default Response updateCompletedStatus(String token, String id, Record record){
        return patchRequest(ENDPOINT_UPDATE_COMPLETED_STATUS + id, token, record);
    };
    default Response updateNote(String token, String id, Record record){
        return putRequest(ENDPOINT_UPDATE_NOTE + id, token, record);
    };

}
