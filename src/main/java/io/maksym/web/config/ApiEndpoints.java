package io.maksym.web.config;

public final class ApiEndpoints {
    private ApiEndpoints() {}

    public static final String BASE_URL = "https://practice.expandtesting.com/notes/api/";
    public static final String ENDPOINT_CREATE_USER = "users/register";
    public static final String ENDPOINT_HEALTH_CHECK = "health-check";
    public static final String ENDPOINT_LOG_IN = "users/login";
    public static final String ENDPOINT_GET_USER_PROFILE = "users/profile";
    public static final String ENDPOINT_UPDATE_USER_PROFILE = "users/profile";
    public static final String ENDPOINT_DELETE_USER_PROFILE = "users/delete-account";
    public static final String ENDPOINT_CREATE_NOTE = "notes";
    public static final String ENDPOINT_GET_ALL_NOTES = "notes";
    public static final String ENDPOINT_GET_NOTE_BY_ID = "notes/";
    public static final String ENDPOINT_DELETE_NOTE = "notes/";
    public static final String ENDPOINT_UPDATE_COMPLETED_STATUS = "notes/";
    public static final String ENDPOINT_UPDATE_NOTE = "notes/";
}
