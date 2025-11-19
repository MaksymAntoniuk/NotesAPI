package io.maksym.web.dto.HealthCheck;

import lombok.Data;

@Data
public class BaseResponse {
    String message;
    int status;
    boolean success;
}
