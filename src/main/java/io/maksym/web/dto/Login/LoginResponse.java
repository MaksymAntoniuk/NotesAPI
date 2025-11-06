package io.maksym.web.dto.Login;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@Data
public class LoginResponse {
    String message;
    int status;
    boolean success;
    @JsonProperty("data")
    LoginData data;
}
