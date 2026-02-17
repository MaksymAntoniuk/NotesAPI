package io.maksym.web.records.ui;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MyNoteUser {
    private String email;
    private String password;
}
