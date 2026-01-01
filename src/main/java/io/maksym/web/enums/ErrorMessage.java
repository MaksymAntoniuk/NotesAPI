package io.maksym.web.enums;

public enum ErrorMessage {

    LOGIN_SUCCESSFUL_MESSAGE("Login successful"),
    HEALTH_CHECK_MESSAGE("Notes API is Running"),
    REGISTRATION_SUCCESSFUL_MESSAGE("User account created successfully"),
    EMAIL_MISSED_MESSAGE("A valid email address is required"),
    NAME_MISSED_MESSAGE("User name must be between 4 and 30 characters"),
    PASSWORD_MISSED_MESSAGE("Password must be between 6 and 30 characters"),
    EXISTING_EMAIL_MESSAGE("An account already exists with the same email address"),
    PROFILE_SUCCESSFUL("Profile successful"),
    UNAUTHORIZED_MESSAGE("Access token is not valid or has expired, you will need to login"),
    INVALID_REQUEST_MESSAGE("Invalid Request"),
    SUCCESSFUL_DELETION_MESSAGE("Account successfully deleted"),
    SUCCESSFUL_CREATION_NOTE("Note successfully created"),
    SUCCESSFUL_DELETION_NOTE("Note successfully deleted"),
    SUCCESSFUL_FETCH_ALL_NOTES("Notes successfully retrieved"),
    NOTE_NOT_FOUND("No note was found with the provided ID, Maybe it was deleted"),

    USERNAME_MISSED_MESSAGE("All fields are required."),
    USERNAME_SHORT_MESSAGE("Username must be at least 3 characters long."),
    USERNAME_LONG_MESSAGE("Invalid username. Usernames can only contain lowercase letters, numbers, and single hyphens, must be between 3 and 39 characters, and cannot start or end with a hyphen."),

    PASSWORD_SHORT_MESSAGE("Password must be at least 4 characters long."),
    PASSWORD_LONG_MESSAGE("Password must be at least 4 characters long."),
    PASSWORD_MISSED("All fields are required."),
    PASSWORD_MISMATCH_MESSAGE("Passwords do not match."),

    ALL_FIELDS_REQUIRED_MESSAGE("All fields are required."),
    UNEXPECTED_ERROR("An error occurred during registration. Please try again.");


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
