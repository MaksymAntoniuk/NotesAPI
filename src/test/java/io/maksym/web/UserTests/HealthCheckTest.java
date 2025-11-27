package io.maksym.web.UserTests;

import io.maksym.web.base.BaseTest;
import io.maksym.web.dto.HealthCheck.BaseResponse;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static io.maksym.web.enums.ErrorMessage.HEALTH_CHECK_MESSAGE;
import static io.maksym.web.enums.StatusCode.SUCCESSFUL_STATUS;
import static io.maksym.web.util.Constants.EXPECTED_SUCCESS_TRUE;
import static io.maksym.web.util.Constants.REPEAT_COUNT;
import static io.maksym.web.util.SchemaResponseValidator.assertResponseSchema;
import static org.junit.jupiter.api.Assertions.*;
@Epic("User API")
@Feature("Check Health of API")
@DisplayName("Verify Successfull Health Check")
@Severity(io.qameta.allure.SeverityLevel.CRITICAL)
public class HealthCheckTest extends BaseTest {
    @DisplayName("Verify Successfull Health Check")
    @Test
    void verifySuccessfullHealthCheck() {
        Response responseValidationSchema = checkHealthOfApiService();
        boolean validationSchema = assertResponseSchema("healthcheck-schema.json", responseValidationSchema);
        BaseResponse response = responseValidationSchema.as(BaseResponse.class);

        assertAll("Verify Successfull Health Check",
                ()->assertTrue(validationSchema, "Incorrect response schema"),
                ()->assertEquals(SUCCESSFUL_STATUS.getStatus(), response.getStatus(), "Incorrect status code"),
                ()->assertEquals(HEALTH_CHECK_MESSAGE.getMessage(), response.getMessage(), "Incorrect message"),
                ()->assertEquals(EXPECTED_SUCCESS_TRUE, response.isSuccess(), "Incorrect success status")
        );

    }
}
