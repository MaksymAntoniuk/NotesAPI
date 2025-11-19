package io.maksym.web.DTO.Login;

import com.fasterxml.jackson.annotation.JsonProperty;


@lombok.Data
public class LoginResponse {
    String message;
    int status;
    boolean success;
    @JsonProperty("data")
    LoginData data;
}
