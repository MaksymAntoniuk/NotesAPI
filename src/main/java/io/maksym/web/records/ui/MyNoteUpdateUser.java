package io.maksym.web.records.ui;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class MyNoteUpdateUser {
    private String fullName;
    private String phone;
    private String company;
}
