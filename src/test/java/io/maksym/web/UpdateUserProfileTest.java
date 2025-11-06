package io.maksym.web;

import io.maksym.web.Records.UserUpdateBody;
import io.maksym.web.base.BaseTest;
import io.maksym.web.dto.Profile.ProfileResponse;
import io.maksym.web.util.DataGenerators;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static io.maksym.web.config.ApiEndpoints.ENDPOINT_UPDATE_USER_PROFILE;
import static io.maksym.web.enums.ErrorMessage.*;
import static io.maksym.web.enums.StatusCode.*;
import static io.maksym.web.util.Constants.*;
import static io.maksym.web.util.SchemaResponseValidator.assertResponseSchema;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UpdateUserProfileTest extends BaseTest {
    public Stream<? extends Arguments> updateUserProfilePositiveTestProvider() {
        DataGenerators generators = new DataGenerators();
        return Stream.of(
                Arguments.of("Verify that user is able to update [Name]", new UserUpdateBody(generators.generateRandomName(4, 30), "", "")),
                Arguments.of("Verify that user is able to update [Phone]", new UserUpdateBody(generators.generateRandomName(4, 30), generators.generateRandomPhone(), "")),
                Arguments.of("Verify that user is able to update [Company]", new UserUpdateBody(generators.generateRandomName(4, 30), "", generators.generateRandomCompany())),
                Arguments.of("Verify that user is able to update [Name], [Phone], [Company] in the same time", new UserUpdateBody(generators.generateRandomName(4, 30), generators.generateRandomPhone(), generators.generateRandomCompany())));
    }
    public Stream<? extends Arguments> updateUserProfileNegativeTestProvider() {
        DataGenerators generators = new DataGenerators();
        return Stream.of(
                Arguments.of("Verify that user is NOT able to update Profile with invalid Token", new UserUpdateBody(generators.generateRandomName(4, 30),  new DataGenerators().generateRandomPhone(), new DataGenerators().generateRandomCompany()), "wrongToken", UNAUTHORIZED_STATUS.getStatus(), EXPECTED_SUCCESS_FALSE, UNAUTHORIZED_MESSAGE.getMessage()),
                Arguments.of("Verify that user is able to update with NULL value in [Name], [Phone], [Company]", new UserUpdateBody("", "", ""), token, UNAUTHORIZED_STATUS.getStatus(), EXPECTED_SUCCESS_FALSE, UNAUTHORIZED_MESSAGE.getMessage())
        );
    }

    @DisplayName("Verify user profile updates with valid values")
    @ParameterizedTest(name = "{0}")
    @MethodSource("updateUserProfilePositiveTestProvider")
    public void updateUserProfileWithValidValuesTest( String testDescription, UserUpdateBody userUpdateBody) {
        Response responseSchemaValidation = patchRequest(ENDPOINT_UPDATE_USER_PROFILE, token, userUpdateBody);

        boolean validationSchema = assertResponseSchema("update-profile-response-schema.json", responseSchemaValidation);

        ProfileResponse response = responseSchemaValidation.as(ProfileResponse.class);

        assertAll(testDescription,
                () -> assertTrue(validationSchema, "Incorrect response schema"),
                () -> Assertions.assertEquals(SUCCESSFUL_STATUS.getStatus(), response.getStatus(), "Incorrect status code"),
                () -> Assertions.assertEquals(EXPECTED_SUCCESS_TRUE, response.isSuccess(), "Incorrect success status"),
                () -> Assertions.assertEquals(email, response.getData().getEmail(), "Incorrect [Email]]"),
                () -> Assertions.assertEquals(userUpdateBody.name(), response.getData().getName(), "Incorrect [Name]"),
                () -> Assertions.assertEquals(userUpdateBody.phone(), response.getData().getPhone(), "Incorrect [Phone]"),
                () -> Assertions.assertEquals(userUpdateBody.company(), response.getData().getCompany(), "Incorrect [Company]")
        );
    }

    @DisplayName("Verify user is NOT able to update profile with invalid values")
    @ParameterizedTest(name = "{0}")
    @MethodSource("updateUserProfileNegativeTestProvider")
    public void updateUserProfileWithInvalidValuesTest(String testDescription, UserUpdateBody userUpdateBody, String token, int statusCode, boolean expectedSuccess, String expectedMessage){

        ProfileResponse response = patchRequest(ENDPOINT_UPDATE_USER_PROFILE, token, userUpdateBody).as(ProfileResponse.class);

        assertAll(testDescription,
                () -> Assertions.assertEquals(statusCode, response.getStatus(), "Incorrect status code"),
                () -> Assertions.assertEquals(expectedSuccess, response.isSuccess(), "Incorrect success status"),
                () -> Assertions.assertEquals(expectedMessage, response.getMessage(), "Incorrect message")
        );
    }

}
