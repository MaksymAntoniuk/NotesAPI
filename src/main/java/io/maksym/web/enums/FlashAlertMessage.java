package io.maksym.web.enums;

public enum FlashAlertMessage {

    SUCCESSFUL_LOGIN_ALERT("You logged into a secure area!"),
    INVALID_USERNAME_ALERT("Your username is invalid!"),
    INVALID_PASSWORD_ALERT("Your password is invalid!"),
    LOGOUT_ALERT("You logged out of the secure area!");

    private final String message;

    FlashAlertMessage(String message) {
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
