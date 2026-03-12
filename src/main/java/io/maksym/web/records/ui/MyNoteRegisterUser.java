package io.maksym.web.records.ui;

import lombok.Data;

public record MyNoteRegisterUser (
     String email,
     String username,
     String password,
     String confirmPassword
){}
