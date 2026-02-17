package io.maksym.web.test_data;

import io.maksym.web.records.ui.MyNoteUser;

public class TestUsers {
    public static MyNoteUser validUser(){
        String email = "mma@gmail.com";
        String password = "mma@gmail.com";

        return new MyNoteUser(
                email,
                password
        );
    }
}
