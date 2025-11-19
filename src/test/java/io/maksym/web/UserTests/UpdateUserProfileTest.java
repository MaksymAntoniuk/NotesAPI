package io.maksym.web.UserTests;

import io.maksym.web.Records.LoginBody;
import io.maksym.web.Records.UserBody;
import io.maksym.web.Records.UserUpdateBody;
import io.maksym.web.requests.actions.SimpleAction;
import io.maksym.web.base.BaseTest;
import io.maksym.web.dto.Profile.ProfileResponse;
import io.maksym.web.util.DataGenerators;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static io.maksym.web.enums.ErrorMessage.NAME_MISSED_MESSAGE;
import static io.maksym.web.enums.ErrorMessage.UNAUTHORIZED_MESSAGE;
import static io.maksym.web.enums.StatusCode.*;
import static io.maksym.web.util.Constants.*;
import static io.maksym.web.util.Constants.EXPECTED_SUCCESS_TRUE;
import static io.maksym.web.util.SchemaResponseValidator.assertResponseSchema;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

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
                Arguments.of("Verify that user is NOT able to update with NULL value in [Name], [Phone], [Company]", new UserUpdateBody("", "", ""), token, BAD_REQUEST_STATUS.getStatus(), EXPECTED_SUCCESS_FALSE, NAME_MISSED_MESSAGE.getMessage())
        );
    }

    @DisplayName("Verify user profile updates with valid values")
    @ParameterizedTest(name = "{0}")
    @MethodSource("updateUserProfilePositiveTestProvider")
    public void updateUserProfileWithValidValuesTest( String testDescription, UserUpdateBody userUpdateBody) {
        String name = new DataGenerators().generateRandomName(NAME_MIN_LENGTH, NAME_MAX_LENGTH);
        String email = new DataGenerators().generateRandomEmail(true);
        String password = new DataGenerators().generateRandomPassword(PASSWORD_MIN_LENGTH, PASSWORD_MAX_LENGTH);

        Response createUser = registerUser(new UserBody(name, email, password));
        assertResponseSchema("registration-response-schema.json", createUser);
        assertEquals(HttpStatus.SC_CREATED, createUser.getStatusCode(), "Incorrect status code");

        Response logInUser = logInUser(new LoginBody(email, password));
        assertResponseSchema("login-response-schema.json", logInUser);
        assertEquals(HttpStatus.SC_OK, logInUser.getStatusCode(), "Incorrect status code");

        String token = logInUser.getBody().jsonPath().getString("data.token");
        System.out.println("Token: " + token);

        Response responseSchemaValidation = updateUser(token, userUpdateBody);

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
        deleteUserProfile(token);
    }

    @DisplayName("Verify user is NOT able to update profile with invalid values")
    @ParameterizedTest(name = "{0}")
    @MethodSource("updateUserProfileNegativeTestProvider")
    public void updateUserProfileWithInvalidValuesTest(String testDescription, UserUpdateBody userUpdateBody, String token, int statusCode, boolean expectedSuccess, String expectedMessage){
        ProfileResponse response = updateUser(token, userUpdateBody).as(ProfileResponse.class);

        assertAll(testDescription,
                () -> Assertions.assertEquals(statusCode, response.getStatus(), "Incorrect status code"),
                () -> Assertions.assertEquals(expectedSuccess, response.isSuccess(), "Incorrect success status"),
                () -> Assertions.assertEquals(expectedMessage, response.getMessage(), "Incorrect message")
        );
    }

}
