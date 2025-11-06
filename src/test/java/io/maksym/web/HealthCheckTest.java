package io.maksym.web;

import io.maksym.web.base.BaseTest;
import io.maksym.web.dto.HealthCheck.HealthCheckResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;

import static io.maksym.web.config.ApiEndpoints.ENDPOINT_HEALTH_CHECK;
import static io.maksym.web.enums.ErrorMessage.HEALTH_CHECK_MESSAGE;
import static io.maksym.web.enums.StatusCode.SUCCESSFUL_STATUS;
import static io.maksym.web.util.Constants.EXPECTED_SUCCESS_TRUE;
import static io.maksym.web.util.Constants.REPEAT_COUNT;
import static io.maksym.web.util.SchemaResponseValidator.assertResponseSchema;
import static org.junit.jupiter.api.Assertions.*;

public class HealthCheckTest extends BaseTest {
    @RepeatedTest(value = REPEAT_COUNT, name = "{displayName} : {currentRepetition}/{totalRepetitions}")
    @DisplayName("Verify Successfull Health Check")
    void verifySuccessfullHealthCheck() {
        Response responseValidationSchema = getCheckHealth(ENDPOINT_HEALTH_CHECK);
        boolean validationSchema = assertResponseSchema("healthcheck-schema.json", responseValidationSchema);
        HealthCheckResponse response = responseValidationSchema.as(HealthCheckResponse.class);

        assertAll("Verify Successfull Health Check",
                ()->assertTrue(validationSchema, "Incorrect response schema"),
                ()->assertEquals(SUCCESSFUL_STATUS.getStatus(), response.getStatus(), "Incorrect status code"),
                ()->assertEquals(HEALTH_CHECK_MESSAGE.getMessage(), response.getMessage(), "Incorrect message"),
                ()->assertEquals(EXPECTED_SUCCESS_TRUE, response.isSuccess(), "Incorrect success status")
        );

    }
}
