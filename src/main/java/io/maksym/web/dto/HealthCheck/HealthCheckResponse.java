package io.maksym.web.dto.HealthCheck;

import lombok.Data;

@Data
public class HealthCheckResponse {
    String message;
    int status;
    boolean success;
}
