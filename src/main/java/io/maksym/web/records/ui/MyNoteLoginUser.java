package io.maksym.web.records.ui;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class MyNoteLoginUser {
    private String email;
    private String password;

}
