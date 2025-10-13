package io.maksym.web.enums;

public enum ErrorMessage {

    LOGIN_SUCCESSFUL_MESSAGE("Login successful"),
    HEALTH_CHECK_MESSAGE("Notes API is Running"),
    REGISTRATION_SUCCESSFUL_MESSAGE("User account created successfully"),
    EMAIL_MISSED_MESSAGE("A valid email address is required"),
    NAME_MISSED_MESSAGE("User name must be between 4 and 30 characters"),
    PASSWORD_MISSED_MESSAGE("Password must be between 6 and 30 characters"),
    EXISTING_EMAIL_MESSAGE("An account already exists with the same email address");


    public final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }


    @Override
    public String toString() {
        return super.toString();
    }
}
