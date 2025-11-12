package io.maksym.web;

import io.maksym.web.base.BaseTest;
import io.maksym.web.dto.Profile.ProfileResponse;
import io.maksym.web.util.SchemaResponseValidator;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;

import static io.maksym.web.enums.ErrorMessage.PROFILE_SUCCESSFUL;
import static io.maksym.web.enums.ErrorMessage.UNAUTHORIZED_MESSAGE;
import static io.maksym.web.enums.StatusCode.SUCCESSFUL_STATUS;
import static io.maksym.web.enums.StatusCode.UNAUTHORIZED_STATUS;
import static io.maksym.web.util.Constants.*;
import static io.maksym.web.util.SchemaResponseValidator.assertResponseSchema;
import static org.junit.jupiter.api.Assertions.*;

public class GetUserProfileTest extends BaseTest {

    @RepeatedTest(value = REPEAT_COUNT, name = "{displayName} : {currentRepetition}/{totalRepetitions}")
    @DisplayName("Verify that user is able to fetch [Profile] data")
    public void getUserProfileTest(){
        System.out.println("token: " + token);
        Response responseValidationSchema = getUserProfileData(token);
        boolean validationSchema = assertResponseSchema("user-profile-response-schema.json", responseValidationSchema);
        ProfileResponse response = responseValidationSchema.as(ProfileResponse.class);

        Assertions.assertAll("Verify that user is able to fetch [Profile] data",
                () -> assertTrue(validationSchema, "Incorrect response schema"),
                () -> assertEquals(SUCCESSFUL_STATUS.getStatus(), response.getStatus(), "Incorrect status code"),
                () -> assertEquals(EXPECTED_SUCCESS_TRUE, response.isSuccess(), "Incorrect success status"),
                () -> assertEquals(PROFILE_SUCCESSFUL.getMessage(), response.getMessage()),

                () -> assertEquals(email, response.getData().getEmail(), "Incorrect [Email]"),
                () -> assertEquals(name, response.getData().getName(), "Incorrect [Name]"),
                () -> assertEquals(id, response.getData().getId(), "Incorrect [Id]")
        );
    }

    @RepeatedTest(value = REPEAT_COUNT, name = "{displayName} : {currentRepetition}/{totalRepetitions}")
    @DisplayName("Verify that user is NOT able to fetch [Profile] data with Invalid Token")
    public void getUserProfileWithWrongTokenTest(){
        Response responseSchemaValidation = getUserProfileData("wrongToken");

        boolean validationSchema = SchemaResponseValidator.assertResponseSchema("healthcheck-schema.json", responseSchemaValidation);
        ProfileResponse response = responseSchemaValidation.as(ProfileResponse.class);

        assertAll("Verify that user is NOT able to fetch [Profile] data with Invalid Token",
                () -> assertTrue(validationSchema, "Incorrect response schema"),
                () -> assertEquals(UNAUTHORIZED_STATUS.getStatus(), response.getStatus(), "Incorrect status code"),
                () -> assertEquals(EXPECTED_SUCCESS_FALSE, response.isSuccess(), "Incorrect success status"),
                () -> assertEquals(UNAUTHORIZED_MESSAGE.getMessage(), response.getMessage(), "Incorrect message")
        );
    }
}
