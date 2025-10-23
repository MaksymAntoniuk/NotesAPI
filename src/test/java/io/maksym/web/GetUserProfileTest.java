package io.maksym.web;

import io.maksym.web.base.BaseTest;
import io.maksym.web.dto.Profile.ProfileResponse;
import org.junit.jupiter.api.Assertions;
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
    public void getUserProfileTest(){
        ProfileResponse response = getUserProfile(token);
        assertEquals(SUCCESSFUL_STATUS.getStatus(), response.getStatus(), "Incorrect status code");
        assertEquals(EXPECTED_SUCCESS_TRUE, response.getSuccess(), "Incorrect success status");
        assertEquals(PROFILE_SUCCESSFUL.getMessage(), response.getMessage());

        Assertions.assertAll("User Profile Response",
                () -> assertEquals(email, response.getData().getEmail(), "Incorrect email"),
                () -> assertEquals(name, response.getData().getName(), "Incorrect name"),
                () -> assertEquals(id, response.getData().getId(), "Incorrect id")
        );
    }

    @RepeatedTest(value = REPEAT_COUNT, name = "{displayName} : {currentRepetition}/{totalRepetitions}")
    public void getUserProfileWithWrongTokenTest(){
        ProfileResponse response = getUserProfile("wrongToken");
        assertAll("Unauthorized Profile Response",
                () -> assertEquals(UNAUTHORIZED_STATUS.getStatus(), response.getStatus(), "Incorrect status code"),
                () -> assertEquals(EXPECTED_SUCCESS_FALSE, response.getSuccess(), "Incorrect success status"),
                () -> assertEquals(UNAUTHORIZED_MESSAGE.getMessage(), response.getMessage(), "Incorrect message")
        );
    }
}
