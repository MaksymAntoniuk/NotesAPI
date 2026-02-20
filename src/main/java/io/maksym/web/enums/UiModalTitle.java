package io.maksym.web.enums;

public enum UiModalTitle {
    DELETE_ACCOUNT_MODAL_TITLE("Do you really want to delete your account?"),
    DELETE_NOTE_MODAL_TITLE("Delete note?");

    public final String message;

    UiModalTitle(String message) {
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
