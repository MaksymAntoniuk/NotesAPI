package io.maksym.web;

import io.maksym.web.base.BaseTest;
import io.maksym.web.dto.Profile.ProfileResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;

import static io.maksym.web.enums.ErrorMessage.PROFILE_SUCCESSFUL;
import static io.maksym.web.enums.ErrorMessage.UNAUTHORIZED_MESSAGE;
import static io.maksym.web.enums.StatusCode.SUCCESSFUL_STATUS;
import static io.maksym.web.enums.StatusCode.UNAUTHORIZED_STATUS;
import static io.maksym.web.util.Constants.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetUserProfileTest extends BaseTest {

    @RepeatedTest(value = REPEAT_COUNT, name = "{displayName} : {currentRepetition}/{totalRepetitions}")
    @DisplayName("Verify that user is able to fetch [Profile] data")
    public void getUserProfileTest(){
        ProfileResponse response = getUserProfile(token);
        assertEquals(SUCCESSFUL_STATUS.getStatus(), response.getStatus(), "Incorrect status code");
        assertEquals(EXPECTED_SUCCESS_TRUE, response.isSuccess(), "Incorrect success status");
        assertEquals(PROFILE_SUCCESSFUL.getMessage(), response.getMessage());

        Assertions.assertAll("Verify that user is able to fetch [Profile] data",
                () -> assertEquals(email, response.getData().getEmail(), "Incorrect [Email]"),
                () -> assertEquals(name, response.getData().getName(), "Incorrect [Name]"),
                () -> assertEquals(id, response.getData().getId(), "Incorrect [Id]")
        );
    }

    @RepeatedTest(value = REPEAT_COUNT, name = "{displayName} : {currentRepetition}/{totalRepetitions}")
    @DisplayName("Verify that user is NOT able to fetch [Profile] data with Invalid Token")
    public void getUserProfileWithWrongTokenTest(){
        ProfileResponse response = getUserProfile("wrongToken");
        assertAll("Verify that user is NOT able to fetch [Profile] data with Invalid Token",
                () -> assertEquals(UNAUTHORIZED_STATUS.getStatus(), response.getStatus(), "Incorrect status code"),
                () -> assertEquals(EXPECTED_SUCCESS_FALSE, response.isSuccess(), "Incorrect success status"),
                () -> assertEquals(UNAUTHORIZED_MESSAGE.getMessage(), response.getMessage(), "Incorrect message")
        );
    }
}
