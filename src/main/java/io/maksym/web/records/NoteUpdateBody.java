package io.maksym.web.records;

public record NoteUpdateBody(String title, String description, Boolean completed, String category){
}
