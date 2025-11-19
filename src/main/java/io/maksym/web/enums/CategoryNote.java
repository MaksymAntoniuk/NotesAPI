package io.maksym.web.enums;

public enum CategoryNote {
    HOME("Home"),
    WORK("Work"),
    PERSONAL("Personal");

    private final String category;

    CategoryNote(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }
}
