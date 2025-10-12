package io.maksym.web;

import lombok.Data;

@Data
public class HealthCheckResponse {
    String message;
    int status;
    boolean success;
}
