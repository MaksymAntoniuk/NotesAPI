package io.maksym.web.records.ui;

import io.maksym.web.enums.CategoryNote;

public record MyNoteBody (String title, String description, CategoryNote category, Boolean completed){
}
