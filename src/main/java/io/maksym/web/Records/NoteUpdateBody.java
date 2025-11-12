package io.maksym.web.Records;

public record NoteUpdateBody(String title, String description, Boolean completed, String category){
}
