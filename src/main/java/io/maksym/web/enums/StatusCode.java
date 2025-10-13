package io.maksym.web.enums;

public enum StatusCode {
    SUCCESSFUL_STATUS(200),
    CREATED_STATUS(201),
    BAD_REQUEST_STATUS(400),
    UNAUTHORIZED_STATUS(401),
    FORBIDDEN_STATUS(403),
    CONFLICT_STATUS(409);

    final int status;

    StatusCode(int status){
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
