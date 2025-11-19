package io.maksym.web.dto.Note;

import lombok.Data;

import java.util.List;

@Data
public class NoteList {
        private boolean success;
        private int status;
        private String message;
        private List<NoteResponse> data;
}
