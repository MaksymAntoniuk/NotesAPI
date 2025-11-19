package io.maksym.web.dto.Note;

import lombok.Data;

@Data
public class Note {
    private boolean success;
    private int status;
    private String message;
    private NoteResponse data;
}
