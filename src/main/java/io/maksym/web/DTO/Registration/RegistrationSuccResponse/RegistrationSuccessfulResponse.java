package io.maksym.web.Registration.RegistrationSuccResponse;


import lombok.Data;

@Data
public class RegistrationSuccessfulResponse {
    String message;
    int status;
    boolean success;
    UserData data;
}
