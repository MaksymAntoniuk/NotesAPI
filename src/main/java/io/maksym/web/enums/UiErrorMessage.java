package io.maksym.web.enums;

public enum UiErrorMessage {

    ACCOUNT_EXISTS_MESSAGE("An account already exists with the same email address"),
    SUCCESSFUL_PROFILE_UPDATE_MESSAGE("Profile updated successful"),
    SUCCESSFUL_ACCOUNT_DELETION_MESSAGE("Your account has been deleted. You should create a new account to continue."),
    INVALID_LOGIN_MESSAGE("Incorrect email address or password");

    public final String message;

    UiErrorMessage(String message) {
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
