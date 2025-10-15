package io.maksym.web;

import io.maksym.web.base.BaseTest;
import io.maksym.web.dto.HealthCheck.HealthCheckResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;

import static io.maksym.web.enums.ErrorMessage.HEALTH_CHECK_MESSAGE;
import static io.maksym.web.enums.StatusCode.SUCCESSFUL_STATUS;
import static io.maksym.web.util.Constants.EXPECTED_SUCCESS;
import static io.maksym.web.util.Constants.REPEAT_COUNT;

public class HealthCheckTest extends BaseTest {
    @RepeatedTest(value = REPEAT_COUNT, name = "{displayName} : {currentRepetition}/{totalRepetitions}")
    @DisplayName("Verify Successfull Health Check")

    void verifySuccessfullHealthCheck() {

        HealthCheckResponse response = checkHealth();
        System.out.println(response);

        Assertions.assertAll("Health Check Response",
                ()->Assertions.assertEquals(SUCCESSFUL_STATUS.getStatus(), response.getStatus(), "Incorrect status code"),
                ()->Assertions.assertEquals(HEALTH_CHECK_MESSAGE.getMessage(), response.getMessage(), "Incorrect message"),
                ()->Assertions.assertEquals(EXPECTED_SUCCESS, response.isSuccess(), "Incorrect success status")
        );
    }
}
