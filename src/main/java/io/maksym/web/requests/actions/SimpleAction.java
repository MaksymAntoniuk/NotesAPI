package io.maksym.web.requests.actions;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.maksym.web.config.ApiEndpoints.*;
import static io.maksym.web.requests.library.RequestLibrary.*;

public interface SimpleAction {
    @Step("Check Health of API Service")
    default Response checkHealthOfApiService(){
        return getCheckHealth(ENDPOINT_HEALTH_CHECK);
    };

    @Step("Fetch user profile with Token: {token}")
    default Response getUserProfileData(String token){
        return getRequest(ENDPOINT_GET_USER_PROFILE, token);
    };

    @Step("Update user profile with record: {record} and Token: {token}")
    default Response updateUser(String token, Record record){
        return patchRequest(ENDPOINT_UPDATE_USER_PROFILE, token, record);
    };

    @Step("Delete user profile with Token: {token}")
    default Response deleteUserProfile(String token){
        return deleteRequest(ENDPOINT_DELETE_USER_PROFILE, token);
    };

    @Step("Register user with record: {record}")
    default Response registerUser(Record record){
        return postRequest(ENDPOINT_CREATE_USER, record);
    }

    @Step("Log in user with record: {record}")
    static Response logInUser(Record record){
        return postRequest(ENDPOINT_LOG_IN, record);
    }

    @Step("Create new Note with record: {record} and User token: {token}")
    static Response createNote(String token, Record record){
        return postRequest(ENDPOINT_CREATE_NOTE, token, record);
    }

    @Step("Fetch all Notes for User with token: {token}")
    default Response getAllNotes(String token){
        return getRequest(ENDPOINT_GET_ALL_NOTES, token);
    }

    @Step("Fetch Note by Id: {id} for User with token: {token}")
    default Response getNoteById(String token, String id){
        return getRequest(ENDPOINT_GET_NOTE_BY_ID + id, token);
    }

    @Step("Delete Note by ID with ID: {id} by User with token: {token}")
    default Response deleteNoteById(String token, String id){
        return deleteRequest(ENDPOINT_DELETE_NOTE + id, token);
    }

    @Step("Update Status of Note with ID: {id} and record: {record} by User with token: {token}")
    default Response updateCompletedStatus(String token, String id, Record record){
        return patchRequest(ENDPOINT_UPDATE_COMPLETED_STATUS + id, token, record);
    };

    @Step("Update Note with ID: {id} and record: {record} by User with token: {token}")
    default Response updateNote(String token, String id, Record record){
        return putRequest(ENDPOINT_UPDATE_NOTE + id, token, record);
    };
}
