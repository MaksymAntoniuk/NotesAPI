package io.maksym.web.DTO.HealthCheck;

import lombok.Data;

@Data
public class HealthCheckResponse {
    String message;
    int status;
    boolean success;
}
