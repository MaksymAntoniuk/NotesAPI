package io.maksym.web;

import com.github.javafaker.Faker;
import io.maksym.web.Records.LoginBody;
import io.maksym.web.Records.UserBody;
import io.maksym.web.base.BaseTest;
import io.maksym.web.dto.Login.LoginResponse;
import io.maksym.web.dto.Registration.RegistrationSuccResponse.RegistrationSuccessfulResponse;
import io.maksym.web.util.DataGenerators;
import io.maksym.web.util.SchemaResponseValidator;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static io.maksym.web.config.ApiEndpoints.*;
import static io.maksym.web.enums.ErrorMessage.*;
import static io.maksym.web.enums.StatusCode.*;
import static io.maksym.web.util.Constants.*;
import static io.maksym.web.util.SchemaResponseValidator.assertResponseSchema;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LoginTest extends BaseTest {

    public Stream<? extends Arguments> negativeTests() {
        return Stream.of(
                arguments("Verify that user is not able to register successfully with empty [Email]", BAD_REQUEST_STATUS.getStatus(), EMAIL_MISSED_MESSAGE.getMessage(), new DataGenerators().generateRandomName(NAME_MIN_LENGTH, NAME_MAX_LENGTH),"", new Faker().internet().password()),
                arguments("Verify that user is not able to register successfully with empty [Name]", BAD_REQUEST_STATUS.getStatus(), NAME_MISSED_MESSAGE.getMessage(), "", new DataGenerators().generateRandomEmail(true), new DataGenerators().generateRandomPassword(PASSWORD_MIN_LENGTH, PASSWORD_MAX_LENGTH)),
                arguments("Verify that user is not able to register successfully with empty [Password]", BAD_REQUEST_STATUS.getStatus(), PASSWORD_MISSED_MESSAGE.getMessage(), new DataGenerators().generateRandomName(NAME_MIN_LENGTH, NAME_MAX_LENGTH), new DataGenerators().generateRandomEmail(true), ""),
                arguments("Verify that user is not able to register successfully with empty [All fields]", BAD_REQUEST_STATUS.getStatus(), NAME_MISSED_MESSAGE.getMessage(), "", "", ""),
                arguments("Verify that user is NOT able to register successfully with [Name] < 4 characters", BAD_REQUEST_STATUS.getStatus(), NAME_MISSED_MESSAGE.getMessage(), new DataGenerators().generateRandomName(1, 3), new DataGenerators().generateRandomEmail(true), new DataGenerators().generateRandomPassword(PASSWORD_MIN_LENGTH, PASSWORD_MAX_LENGTH)),
                arguments("Verify that user is NOT able to register successfully with [Name] > 30 characters", BAD_REQUEST_STATUS.getStatus(), NAME_MISSED_MESSAGE.getMessage(), new DataGenerators().generateRandomName(31, 100), new DataGenerators().generateRandomEmail(true),  new DataGenerators().generateRandomPassword(PASSWORD_MIN_LENGTH, PASSWORD_MAX_LENGTH)),
                arguments("Verify that user is NOT able to register successfully with [Null] in [Name]", BAD_REQUEST_STATUS.getStatus(), NAME_MISSED_MESSAGE.getMessage(), null, new DataGenerators().generateRandomEmail(true), new DataGenerators().generateRandomPassword(PASSWORD_MIN_LENGTH, PASSWORD_MAX_LENGTH)),
                arguments("Verify that user is NOT able to register successfully with existing [Email]", CONFLICT_STATUS.getStatus(), EXISTING_EMAIL_MESSAGE.getMessage(), new DataGenerators().generateRandomName(NAME_MIN_LENGTH, NAME_MAX_LENGTH), email, new DataGenerators().generateRandomPassword(6, 30))
        );
    }

    @DisplayName("Verify that user is able to Register successfully")
    @RepeatedTest(value = REPEAT_COUNT, name = "{displayName} : {currentRepetition}/{totalRepetitions}")
    void verifySuccessUserRegistrationTest() {

        String fakeEmail = new DataGenerators().generateRandomEmail(true);
        String fakeName = new DataGenerators().generateRandomName(NAME_MIN_LENGTH, NAME_MAX_LENGTH);
        String fakePassword = new DataGenerators().generateRandomPassword(PASSWORD_MIN_LENGTH, PASSWORD_MAX_LENGTH);

        UserBody user = new UserBody(fakeName, fakeEmail, fakePassword);

        //Schema validation
        Response responseSchemaValidation = postRequest(ENDPOINT_CREATE_USER, user);
        boolean validationSchema = assertResponseSchema("registration-response-schema.json", responseSchemaValidation);

        RegistrationSuccessfulResponse response = responseSchemaValidation.as(RegistrationSuccessfulResponse.class);

        assertAll("Verify that user is able to Register successfully",
                () -> assertTrue(validationSchema, "Incorrect response schema"),
                () -> assertEquals(UUID_LENGTH, response.getData().getId().length(), "User [ID] is not correct"),
                () -> assertEquals(user.name(), response.getData().getName(), "User [Name] is not correct"),
                () -> assertEquals(user.email().toLowerCase(), response.getData().getEmail().toLowerCase(),"User [Email] is not correct"),
                () -> assertEquals(CREATED_STATUS.getStatus(), response.getStatus()),
                () -> assertEquals(REGISTRATION_SUCCESSFUL_MESSAGE.getMessage(), response.getMessage()),
                () -> assertEquals(EXPECTED_SUCCESS_TRUE, response.isSuccess())
        );
    }

    @DisplayName("Verify that user is able to register successfully with [Name] == 30 characters")
    @RepeatedTest(value = REPEAT_COUNT, name = "{displayName} : {currentRepetition}/{totalRepetitions}")
    void verifySuccessUserRegistrationWithNameEqualTo30Characters() {
        String fakeEmail = new DataGenerators().generateRandomEmail(true);
        String fakePassword = new DataGenerators().generateRandomPassword(PASSWORD_MIN_LENGTH, PASSWORD_MAX_LENGTH);

        UserBody user = new UserBody("NameFakeNameFakeNameFakeNameFa", fakeEmail, fakePassword);

        //Schema validation
        Response responseSchemaValidation = postRequest(ENDPOINT_CREATE_USER, user);
        boolean validationSchema = assertResponseSchema("registration-response-schema.json", responseSchemaValidation);

        RegistrationSuccessfulResponse response = responseSchemaValidation.as(RegistrationSuccessfulResponse.class);

        assertAll("Verify that user is able to register successfully with [Name] == 30 characters",
                () -> assertTrue(validationSchema, "Incorrect response schema"),
                () -> assertEquals(CREATED_STATUS.getStatus(), response.getStatus()),
                () -> assertEquals(REGISTRATION_SUCCESSFUL_MESSAGE.getMessage(), response.getMessage()),
                () -> assertEquals(EXPECTED_SUCCESS_TRUE, response.isSuccess()),
                () -> assertEquals(UUID_LENGTH, response.getData().getId().length(), "User [ID] is not correct"),
                () -> assertEquals(user.name(), response.getData().getName(), "User [Name] is not correct"),
                () -> assertEquals(user.email().toLowerCase(), response.getData().getEmail().toLowerCase(),"User [Email] is not correct")
        );
    }

    @DisplayName("Verify that user is able to register successfully with [Name] == 4 characters")
    @RepeatedTest(value = REPEAT_COUNT, name = "{displayName} : {currentRepetition}/{totalRepetitions}")
    void verifySuccessUserRegistrationWithNameEqualTo4Characters() {
        String fakeEmail = new DataGenerators().generateRandomEmail(true);
        String fakePassword = new DataGenerators().generateRandomPassword(PASSWORD_MIN_LENGTH, PASSWORD_MAX_LENGTH);

        UserBody user = new UserBody("Name", fakeEmail, fakePassword);

        //Schema validation
        Response responseSchemaValidation = postRequest(ENDPOINT_CREATE_USER, user);
        boolean validationSchema = assertResponseSchema("registration-response-schema.json", responseSchemaValidation);

        RegistrationSuccessfulResponse response = responseSchemaValidation.as(RegistrationSuccessfulResponse.class);

        assertAll("Verify that user is able to register successfully with [Name] == 4 characters",
                () -> assertTrue(validationSchema, "Incorrect response schema"),

                () -> assertEquals(UUID_LENGTH, response.getData().getId().length(), "User [ID] is not correct"),
                () -> assertEquals(user.name(), response.getData().getName(), "User [Name] is not correct"),
                () -> assertEquals(user.email().toLowerCase(), response.getData().getEmail().toLowerCase(),"User [Email] is not correct"),

                () -> assertEquals(CREATED_STATUS.getStatus(), response.getStatus()),
                () -> assertEquals(REGISTRATION_SUCCESSFUL_MESSAGE.getMessage(), response.getMessage()),
                () -> assertEquals(EXPECTED_SUCCESS_TRUE, response.isSuccess())
        );
    }


    @MethodSource({"negativeTests"})
    @ParameterizedTest(name = "{0}")
    @DisplayName("Verify that user is NOT able to register successfully")
    void verifyRegistrationWithInvalidDataTest(String testName, int expectedStatusCode, String expectedMessage,String name, String email, String password) {
        UserBody user = new UserBody(name, email, password);

        //Schema validation
        Response responseSchemaValidation = postRequest(ENDPOINT_CREATE_USER, user);
        boolean validationSchema = assertResponseSchema("healthcheck-schema.json", responseSchemaValidation);

        RegistrationSuccessfulResponse response = responseSchemaValidation.as(RegistrationSuccessfulResponse.class);
        assertAll(testName,
                () -> assertTrue(validationSchema, "Incorrect response schema"),
                () -> assertEquals(expectedStatusCode, response.getStatus(), "Incorrect status code"),
                () -> assertEquals(expectedMessage, response.getMessage(), "Incorrect message"),
                () -> assertEquals(EXPECTED_SUCCESS_FALSE, response.isSuccess(), "Incorrect success status")
        );
    }


    @DisplayName("Verify that user is able to Log In with valid credentials")
    @RepeatedTest(value = REPEAT_COUNT, name = "{displayName} : {currentRepetition}/{totalRepetitions}")
    void VerifySuccessfullLogin(){
        //Schema validation
        Response responseSchemaValidation = postRequest(ENDPOINT_LOG_IN, new LoginBody(email, password));
        boolean validationSchema = assertResponseSchema("login-response-schema.json", responseSchemaValidation);

        LoginResponse response = responseSchemaValidation.as(LoginResponse.class);

        assertAll("Verify that user is able to Log In with valid credentials",
                () -> assertTrue(validationSchema, "Incorrect response schema"),
                () -> assertEquals(UUID_LENGTH, response.getData().getId().length(), "User [ID] is not correct"),
                () -> assertEquals(email, response.getData().getEmail(), "User [Email] is not correct"),
                () -> assertEquals(TOKEN_LENGTH, response.getData().getToken().length(), "User [Token] is not correct" ),
                () -> assertEquals(SUCCESSFUL_STATUS.getStatus(), response.getStatus()),
                () -> assertEquals(LOGIN_SUCCESSFUL_MESSAGE.getMessage(), response.getMessage()),
                () -> assertEquals(EXPECTED_SUCCESS_TRUE, response.isSuccess())
        );
    }

}