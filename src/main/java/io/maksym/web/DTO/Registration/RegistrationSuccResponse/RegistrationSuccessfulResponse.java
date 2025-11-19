package io.maksym.web.DTO.Registration.RegistrationSuccResponse;


import lombok.Data;

@Data
public class RegistrationSuccessfulResponse {
    String message;
    int status;
    boolean success;
    UserData data;
}
