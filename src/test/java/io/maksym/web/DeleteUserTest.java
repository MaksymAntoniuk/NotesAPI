package io.maksym.web;

import io.maksym.web.base.BaseTest;
import io.maksym.web.dto.HealthCheck.HealthCheckResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;

import static io.maksym.web.config.ApiEndpoints.ENDPOINT_DELETE_USER_PROFILE;
import static io.maksym.web.enums.ErrorMessage.SUCCESSFUL_DELETION_MESSAGE;
import static io.maksym.web.enums.ErrorMessage.UNAUTHORIZED_MESSAGE;
import static io.maksym.web.enums.StatusCode.SUCCESSFUL_STATUS;
import static io.maksym.web.enums.StatusCode.UNAUTHORIZED_STATUS;
import static io.maksym.web.util.Constants.*;
import static io.maksym.web.util.SchemaResponseValidator.assertResponseSchema;
import static org.junit.jupiter.api.Assertions.*;

public class DeleteUserTest extends BaseTest {
    @RepeatedTest(value = REPEAT_COUNT, name = "{displayName} : {currentRepetition}/{totalRepetitions}")
    @DisplayName("Verify that user is Deleted successfully")
    public void deleteUserTest(){
        HealthCheckResponse response = deleteRequest(ENDPOINT_DELETE_USER_PROFILE, token).as(HealthCheckResponse.class);
        assertAll("Verify that user is Deleted successfully",
                () -> assertEquals(SUCCESSFUL_STATUS.getStatus(), response.getStatus(), "Invalid status code"),
                () -> assertEquals(EXPECTED_SUCCESS_TRUE, response.isSuccess(), "Invalid success status"),
                () -> assertEquals( SUCCESSFUL_DELETION_MESSAGE.getMessage(),response.getMessage(),"Invalid message")
        );

        HealthCheckResponse responseAfterSecondDeletion = deleteRequest(ENDPOINT_DELETE_USER_PROFILE, token).as(HealthCheckResponse.class);
        assertAll("Verify that user not able to Deleted second time",
                () -> assertEquals(UNAUTHORIZED_STATUS.getStatus(), responseAfterSecondDeletion.getStatus(), "Invalid status code"),
                () -> assertEquals(EXPECTED_SUCCESS_FALSE, responseAfterSecondDeletion.isSuccess(), "Invalid success status"),
                () -> assertEquals( UNAUTHORIZED_MESSAGE.getMessage(),responseAfterSecondDeletion.getMessage(),"Invalid message")
        );

    }

    @RepeatedTest(value = REPEAT_COUNT, name = "{displayName} : {currentRepetition}/{totalRepetitions}")
    @DisplayName("Verify that user is NOT able to Delete Profile with invalid Token")
    public void deleteUserWithInvalidTokenTest(){
        Response responseValidationSchema = deleteRequest(ENDPOINT_DELETE_USER_PROFILE, "wrongToken");
        boolean validationSchema = assertResponseSchema("healthcheck-schema.json", responseValidationSchema);
        HealthCheckResponse response = responseValidationSchema.as(HealthCheckResponse.class);

        assertAll("Verify that user is NOT able to Delete Profile with invalid Token",
                () -> assertTrue(validationSchema, "Invalid response schema"),
                () -> assertEquals(UNAUTHORIZED_STATUS.getStatus(), response.getStatus(), "Invalid status code"),
                () -> assertEquals(EXPECTED_SUCCESS_FALSE, response.isSuccess(), "Invalid success status"),
                () -> assertEquals( UNAUTHORIZED_MESSAGE.getMessage(),response.getMessage(),"Invalid message")
        );

    }
}
