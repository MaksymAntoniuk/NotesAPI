package io.maksym.web.UserTests;

import com.github.javafaker.Faker;
import io.maksym.web.Records.UserBody;
import io.maksym.web.base.BaseTest;
import io.maksym.web.dto.Registration.RegistrationSuccResponse.RegistrationSuccessfulResponse;
import io.maksym.web.util.DataGenerators;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static io.maksym.web.enums.ErrorMessage.*;
import static io.maksym.web.enums.StatusCode.*;
import static io.maksym.web.util.Constants.*;
import static io.maksym.web.util.SchemaResponseValidator.assertResponseSchema;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Epic("User API")
@DisplayName("Verify that user is able to register successfully")
@Severity(io.qameta.allure.SeverityLevel.CRITICAL)
class RegisterUserTest extends BaseTest {

    public Stream<? extends Arguments> registerUserWithNegativeTestProvider() {
        return Stream.of(
                arguments("Verify that user is not able to register successfully with empty [Email]",
                        BAD_REQUEST_STATUS.getStatus(),
                        EMAIL_MISSED_MESSAGE.getMessage(),
                        new UserBody(new DataGenerators().generateRandomName(NAME_MIN_LENGTH, NAME_MAX_LENGTH), "",
                                new Faker().internet().password())),
                arguments("Verify that user is not able to register successfully with empty [Name]",
                        BAD_REQUEST_STATUS.getStatus(),
                        NAME_MISSED_MESSAGE.getMessage(),
                        new UserBody("", new DataGenerators().generateRandomEmail(true),
                                new DataGenerators().generateRandomPassword(PASSWORD_MIN_LENGTH, PASSWORD_MAX_LENGTH))),
                arguments("Verify that user is not able to register successfully with empty [Password]",
                        BAD_REQUEST_STATUS.getStatus(),
                        PASSWORD_MISSED_MESSAGE.getMessage(),
                        new UserBody(new DataGenerators().generateRandomName(NAME_MIN_LENGTH, NAME_MAX_LENGTH),
                                new DataGenerators().generateRandomEmail(true), "")),
                arguments("Verify that user is not able to register successfully with empty [All fields]",
                        BAD_REQUEST_STATUS.getStatus(),
                        NAME_MISSED_MESSAGE.getMessage(),
                        new UserBody("", "", "")),
                arguments("Verify that user is NOT able to register successfully with [Name] < 4 characters",
                        BAD_REQUEST_STATUS.getStatus(),
                        NAME_MISSED_MESSAGE.getMessage(),
                        new UserBody(new DataGenerators().generateRandomName(1, 3),
                                new DataGenerators().generateRandomEmail(true),
                                new DataGenerators().generateRandomPassword(PASSWORD_MIN_LENGTH, PASSWORD_MAX_LENGTH))),
                arguments("Verify that user is NOT able to register successfully with [Name] > 30 characters",
                        BAD_REQUEST_STATUS.getStatus(),
                        NAME_MISSED_MESSAGE.getMessage(),
                        new UserBody(new DataGenerators().generateRandomName(31, 100),
                                new DataGenerators().generateRandomEmail(true),
                                new DataGenerators().generateRandomPassword(PASSWORD_MIN_LENGTH, PASSWORD_MAX_LENGTH))),
                arguments("Verify that user is NOT able to register successfully with [Null] in [Name]",
                        BAD_REQUEST_STATUS.getStatus(),
                        NAME_MISSED_MESSAGE.getMessage(),
                        new UserBody(null, new DataGenerators().generateRandomEmail(true),
                                new DataGenerators().generateRandomPassword(PASSWORD_MIN_LENGTH, PASSWORD_MAX_LENGTH)))
                );
    }

    @MethodSource({"registerUserWithNegativeTestProvider"})
    @ParameterizedTest(name = "{0}")
    @DisplayName("Verify that user is NOT able to register successfully")
    void verifyRegistrationWithInvalidDataTest(String testName, int expectedStatusCode, String expectedMessage, UserBody userBody) {
        Response registerUser = registerUser(userBody);
        boolean validationSchema = assertResponseSchema("healthcheck-schema.json", registerUser);

        RegistrationSuccessfulResponse response = registerUser.as(RegistrationSuccessfulResponse.class);
        assertAll(testName,
                () -> assertTrue(validationSchema, "Incorrect response schema"),
                () -> assertEquals(expectedStatusCode, response.getStatus(), "Incorrect status code"),
                () -> assertEquals(expectedMessage, response.getMessage(), "Incorrect message"),
                () -> assertEquals(EXPECTED_SUCCESS_FALSE, response.isSuccess(), "Incorrect success status")
        );
    }

    @DisplayName("Verify that user is able to Register successfully")
    @RepeatedTest(value = REPEAT_COUNT, name = "{displayName} : {currentRepetition}/{totalRepetitions}")
    void verifySuccessUserRegistrationTest() {

        String fakeEmail = new DataGenerators().generateRandomEmail(true);
        String fakeName = new DataGenerators().generateRandomName(NAME_MIN_LENGTH, NAME_MAX_LENGTH);
        String fakePassword = new DataGenerators().generateRandomPassword(PASSWORD_MIN_LENGTH, PASSWORD_MAX_LENGTH);

        UserBody user = new UserBody(fakeName, fakeEmail, fakePassword);

        Response registerUser = registerUser(user);
        boolean validationSchema = assertResponseSchema("registration-response-schema.json",
                registerUser);

        RegistrationSuccessfulResponse response = registerUser.as(RegistrationSuccessfulResponse.class);

        assertAll("Verify that user is able to Register successfully",
                () -> assertTrue(validationSchema, "Incorrect response schema"),
                () -> assertEquals(UUID_LENGTH, response.getData().getId().length(), "User [ID] is not correct"),
                () -> assertEquals(user.name(), response.getData().getName(), "User [Name] is not correct"),
                () -> assertEquals(user.email().toLowerCase(), response.getData().getEmail().toLowerCase(),
                        "User [Email] is not correct"),
                () -> assertEquals(CREATED_STATUS.getStatus(), response.getStatus()),
                () -> assertEquals(REGISTRATION_SUCCESSFUL_MESSAGE.getMessage(), response.getMessage()),
                () -> assertEquals(EXPECTED_SUCCESS_TRUE, response.isSuccess())
        );
    }

    @DisplayName("Verify that user is able to register successfully with [Name] == 30 characters")
    @RepeatedTest(value = REPEAT_COUNT, name = "{displayName} : {currentRepetition}/{totalRepetitions}")
    @Description("""
            1. Enter 30 characters length value in [Name] field
            2. Enter valid value in [Email] field
            3. Enter valid value in [Password] field
            4. Send request
            """)
    void verifySuccessUserRegistrationWithNameEqualTo30Characters() {
        String fakeEmail = new DataGenerators().generateRandomEmail(true);
        String fakePassword = new DataGenerators().generateRandomPassword(PASSWORD_MIN_LENGTH, PASSWORD_MAX_LENGTH);

        UserBody user = new UserBody("NameFakeNameFakeNameFakeNameFa", fakeEmail, fakePassword);

        Response registerUser = registerUser(user);
        boolean validationSchema = assertResponseSchema("registration-response-schema.json", registerUser);

        RegistrationSuccessfulResponse response = registerUser.as(RegistrationSuccessfulResponse.class);

        assertAll("Verify that user is able to register successfully with [Name] == 30 characters",
                () -> assertTrue(validationSchema, "Incorrect response schema"),
                () -> assertEquals(CREATED_STATUS.getStatus(), response.getStatus()),
                () -> assertEquals(REGISTRATION_SUCCESSFUL_MESSAGE.getMessage(), response.getMessage()),
                () -> assertEquals(EXPECTED_SUCCESS_TRUE, response.isSuccess()),
                () -> assertEquals(UUID_LENGTH, response.getData().getId().length(), "User [ID] is not correct"),
                () -> assertEquals(user.name(), response.getData().getName(), "User [Name] is not correct"),
                () -> assertEquals(user.email().toLowerCase(), response.getData().getEmail().toLowerCase(),
                        "User [Email] is not correct")
        );
    }

    @DisplayName("Verify that user is able to register successfully with [Name] == 4 characters")
    @RepeatedTest(value = REPEAT_COUNT, name = "{displayName} : {currentRepetition}/{totalRepetitions}")
    @Description("""
            1. Enter 4 characters length value in [Name] field
            2. Enter valid value in [Email] field
            3. Enter valid value in [Password] field
            4. Send request
            """)
    void verifySuccessUserRegistrationWithNameEqualTo4Characters() {
        String fakeEmail = new DataGenerators().generateRandomEmail(true);
        String fakePassword = new DataGenerators().generateRandomPassword(PASSWORD_MIN_LENGTH, PASSWORD_MAX_LENGTH);

        UserBody user = new UserBody("Name", fakeEmail, fakePassword);

        Response registerUser = registerUser(user);
        boolean validationSchema = assertResponseSchema("registration-response-schema.json", registerUser);

        RegistrationSuccessfulResponse response = registerUser.as(RegistrationSuccessfulResponse.class);

        assertAll("Verify that user is able to register successfully with [Name] == 4 characters",
                () -> assertTrue(validationSchema, "Incorrect response schema"),

                () -> assertEquals(UUID_LENGTH, response.getData().getId().length(), "User [ID] is not correct"),
                () -> assertEquals(user.name(), response.getData().getName(), "User [Name] is not correct"),
                () -> assertEquals(user.email().toLowerCase(), response.getData().getEmail().toLowerCase(),
                        "User [Email] is not correct"),

                () -> assertEquals(CREATED_STATUS.getStatus(), response.getStatus()),
                () -> assertEquals(REGISTRATION_SUCCESSFUL_MESSAGE.getMessage(), response.getMessage()),
                () -> assertEquals(EXPECTED_SUCCESS_TRUE, response.isSuccess())
        );
    }

    @DisplayName("Verify that user is NOT able to register successfully with existing [Email]")
    @Description("""
            1. Enter valid value in [Name] field
            2. Enter already registered Email in [Email] field
            3. Enter valid value in [Password] field
            4. Send request
            """)
    @Test
    void verifyUserIsNotAbleToRegisterWithExistingEmail() {
        String fakeName = new DataGenerators().generateRandomName(NAME_MIN_LENGTH, NAME_MAX_LENGTH);
        String fakeEmail = new DataGenerators().generateRandomEmail(true);
        String fakePassword = new DataGenerators().generateRandomPassword(PASSWORD_MIN_LENGTH, PASSWORD_MAX_LENGTH);

        Response firstRegisterResponse = registerUser(new UserBody(fakeName, fakeEmail, fakePassword));
        boolean validationSchemaOfFirstRegisterResponse = assertResponseSchema("registration-response-schema.json", firstRegisterResponse);

        RegistrationSuccessfulResponse responseBefore = firstRegisterResponse.as(RegistrationSuccessfulResponse.class);

        assertAll("Verify first successfull Registration ",
                () -> assertTrue(validationSchemaOfFirstRegisterResponse, "Incorrect response schema"),
                () -> assertEquals(CREATED_STATUS.getStatus(), responseBefore.getStatus(), "Incorrect status code"),

        () -> assertEquals(REGISTRATION_SUCCESSFUL_MESSAGE.getMessage(), responseBefore.getMessage()),
                () -> assertEquals(EXPECTED_SUCCESS_TRUE, responseBefore.isSuccess())
        );

        Response registerUserSecondTime = registerUser(new UserBody(fakeName, fakeEmail, fakePassword));
        boolean validationSchema = assertResponseSchema("healthcheck-schema.json", registerUserSecondTime);
        RegistrationSuccessfulResponse response = registerUserSecondTime.as(RegistrationSuccessfulResponse.class);

        assertAll("Verify that user is NOT able to register successfully with existing [Email]",
                () -> assertTrue(validationSchema, "Incorrect response schema"),
                () -> assertEquals( CONFLICT_STATUS.getStatus(), response.getStatus(), "Incorrect status code"),
                () -> assertEquals(EXISTING_EMAIL_MESSAGE.getMessage(), response.getMessage(), "Incorrect message"),
                () -> assertEquals(EXPECTED_SUCCESS_FALSE, response.isSuccess(), "Incorrect success status")
        );
    }

}