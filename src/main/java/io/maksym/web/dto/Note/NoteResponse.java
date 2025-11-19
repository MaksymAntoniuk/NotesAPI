package io.maksym.web.dto.Note;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class NoteResponse {
    private String id;
    private String title;
    private String description;
    private boolean completed;
    private String created_at;
    private String updated_at;
    private String category;
    @JsonProperty("user_id")
    private String userId;
}
