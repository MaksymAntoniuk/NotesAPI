package io.maksym.web;

import com.github.javafaker.Faker;
import io.maksym.web.base.BaseTest;
import io.maksym.web.dto.User.User;
import io.maksym.web.util.DataGenerators;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;

import static io.maksym.web.enums.ErrorMessage.*;
import static io.maksym.web.enums.StatusCode.*;
import static io.maksym.web.util.Constants.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;


class LoginTest extends BaseTest {

    @DisplayName("Verify that user is able to Register successfully")
    @RepeatedTest(value = REPEAT_COUNT, name = "{displayName} : {currentRepetition}/{totalRepetitions}")
    void verifySuccessUserRegistrationTest() {

        String fakeEmail = new DataGenerators().generateRandomEmail(true);
        String fakeName = new DataGenerators().generateRandomName(NAME_MIN_LENGTH, NAME_MAX_LENGTH);
        String fakePassword = new DataGenerators().generateRandomPassword(PASSWORD_MIN_LENGTH, PASSWORD_MAX_LENGTH);

        User user = new User(fakeName, fakeEmail, fakePassword);

        var response= createUser(user);

        assertAll("Verify that user is able to Register successfully",
                () -> assertEquals(UUID_LENGTH, response.getData().getId().length(), "User [ID] is not correct"),
                () -> assertEquals(user.getName(), response.getData().getName(), "User [Name] is not correct"),
                () -> assertEquals(user.getEmail().toLowerCase(), response.getData().getEmail().toLowerCase(),"User [Email] is not correct"),
                () -> assertEquals(CREATED_STATUS.getStatus(), response.getStatus()),
                () -> assertEquals(REGISTRATION_SUCCESSFUL_MESSAGE.getMessage(), response.getMessage()),
                () -> assertEquals(EXPECTED_SUCCESS_TRUE, response.isSuccess())
        );
    }


    @DisplayName("Verify that user is not able to register successfully with empty [Email]")
    @RepeatedTest(value = REPEAT_COUNT, name = "{displayName} : {currentRepetition}/{totalRepetitions}")
    void verifyFailUserRegistrationWithEmptyEmail() {
        User user = new User(new Faker().name().firstName(), "", new Faker().internet().password());
        var response= createUser(user);

        assertAll("Verify that user is not able to register successfully with empty [Email]",
                () -> assertEquals(BAD_REQUEST_STATUS.getStatus(), response.getStatus(), "Incorrect status code"),
                () -> assertEquals(EMAIL_MISSED_MESSAGE.getMessage(), response.getMessage(), "Incorrect message"),
                () -> assertEquals(EXPECTED_SUCCESS_FALSE, response.isSuccess(), "Incorrect success status")
        );

    }

    @DisplayName("Verify that user is not able to register successfully with empty [Name]")
    @RepeatedTest(value = REPEAT_COUNT, name = "{displayName} : {currentRepetition}/{totalRepetitions}")
    void verifyFailUserRegistrationWithEmptyName() {
        String fakeEmail = new Faker().artist().name() + System.currentTimeMillis() + "@gmail.com";

        User user = new User("", fakeEmail, new Faker().internet().password());
        var response= createUser(user);

        assertAll("Verify that user is not able to register successfully with empty [Name]",
                () -> assertEquals(BAD_REQUEST_STATUS.getStatus(), response.getStatus(), "Incorrect status code"),
                () -> assertEquals(NAME_MISSED_MESSAGE.getMessage(), response.getMessage(), "Incorrect message"),
                () -> assertEquals(EXPECTED_SUCCESS_FALSE, response.isSuccess(), "Incorrect success status")
        );


    }

    @RepeatedTest(value = REPEAT_COUNT, name = "{displayName} : {currentRepetition}/{totalRepetitions}")
    @DisplayName("Verify that user is not able to register successfully with empty [Password]")
    void verifyFailUserRegistrationWithEmptyPassword() {
        String fakeEmail = new DataGenerators().generateRandomEmail(true);
        String fakeName = new DataGenerators().generateRandomName(NAME_MIN_LENGTH, NAME_MAX_LENGTH);

        User user = new User(fakeName, fakeEmail, "");
        var response= createUser(user);

        assertAll("Verify that user is not able to register successfully with empty [Password]",
                () -> assertEquals(BAD_REQUEST_STATUS.getStatus(), response.getStatus(), "Incorrect status code"),
                () -> assertEquals(PASSWORD_MISSED_MESSAGE.getMessage(), response.getMessage(), "Incorrect message"),
                () -> assertEquals(EXPECTED_SUCCESS_FALSE, response.isSuccess(), "Incorrect success status")
        );



    }

    @RepeatedTest(value = REPEAT_COUNT, name = "{displayName} : {currentRepetition}/{totalRepetitions}")
    @DisplayName("Verify that user is not able to register successfully with empty [All fields]")
    void verifyFailUserRegistrationWithEmptyAllFields() {
        User user = new User("", "", "");
        var response= createUser(user);

        assertAll("Verify that user is not able to register successfully with empty [All fields]",
                () -> assertEquals(BAD_REQUEST_STATUS.getStatus(), response.getStatus(), "Incorrect status code"),
                () ->assertEquals(NAME_MISSED_MESSAGE.getMessage(), response.getMessage(), "Incorrect message"),
                () ->assertEquals(EXPECTED_SUCCESS_FALSE, response.isSuccess(), "Incorrect success status")
        );

    }

    @DisplayName("Verify that user is NOT able to register successfully with [Name] < 4 characters")
    @RepeatedTest(value = REPEAT_COUNT, name = "{displayName} : {currentRepetition}/{totalRepetitions}")
    void verifyFailUserRegistrationWithNameLessThan4Characters() {
        String fakeEmail = new DataGenerators().generateRandomEmail(true);
        String fakeName = new DataGenerators().generateRandomName(1, 3);

        String fakePassword = new DataGenerators().generateRandomPassword(PASSWORD_MIN_LENGTH, PASSWORD_MAX_LENGTH);
        User user = new User(fakeName, fakeEmail, fakePassword);
        var response= createUser(user);

        assertAll("Verify that user is NOT able to register successfully with [Name] < 4 characters",
                () -> assertEquals(BAD_REQUEST_STATUS.getStatus(), response.getStatus(), "Incorrect status code"),
                () -> assertEquals(NAME_MISSED_MESSAGE.getMessage(), response.getMessage(), "Incorrect message"),
                () -> assertEquals(EXPECTED_SUCCESS_FALSE, response.isSuccess(), "Incorrect success status")
        );
    }

    @DisplayName("Verify that user is NOT able to register successfully with [Name] > 30 characters")
    @RepeatedTest(value = REPEAT_COUNT, name = "{displayName} : {currentRepetition}/{totalRepetitions}")
    void verifyFailUserRegistrationWithNameMoreThan4Characters() {
        String fakeEmail = new DataGenerators().generateRandomEmail(true);
        String fakeName = new DataGenerators().generateRandomName(31, 100);
        String fakePassword = new DataGenerators().generateRandomPassword(PASSWORD_MIN_LENGTH, PASSWORD_MAX_LENGTH);

        User user = new User(fakeName, fakeEmail, fakePassword);
        var response= createUser(user);

        assertAll("Verify that user is NOT able to register successfully with [Name] > 30 characters",
                () -> assertEquals(BAD_REQUEST_STATUS.getStatus(), response.getStatus(), "Incorrect status code"),
                () -> assertEquals(NAME_MISSED_MESSAGE.getMessage(), response.getMessage(), "Incorrect message"),
                () -> assertEquals(EXPECTED_SUCCESS_FALSE, response.isSuccess(), "Incorrect success status")
        );
    }

    @DisplayName("Verify that user is NOT able to register successfully with [Null] in [Name]")
    @RepeatedTest(value = REPEAT_COUNT, name = "{displayName} : {currentRepetition}/{totalRepetitions}")
    void verifyFailUserRegistrationWithNullName() {
        String fakeEmail = new DataGenerators().generateRandomEmail(true);
        String fakeName = null;
        String fakePassword = new DataGenerators().generateRandomPassword(PASSWORD_MIN_LENGTH, PASSWORD_MAX_LENGTH);

        User user = new User(fakeName, fakeEmail, fakePassword);

        var response= createUser(user);

        assertAll("Verify that user is NOT able to register successfully with [Null] in [Name]",
                () -> assertEquals(BAD_REQUEST_STATUS.getStatus(), response.getStatus(), "Incorrect status code"),
                () ->assertEquals(NAME_MISSED_MESSAGE.getMessage(), response.getMessage(), "Incorrect message"),
                () ->assertEquals(EXPECTED_SUCCESS_FALSE, response.isSuccess(), "Incorrect success status")
        );
    }

    @DisplayName("Verify that user is able to register successfully with [Name] == 30 characters")
    @RepeatedTest(value = REPEAT_COUNT, name = "{displayName} : {currentRepetition}/{totalRepetitions}")
    void verifySuccessUserRegistrationWithNameEqualTo30Characters() {
        String fakeEmail = new DataGenerators().generateRandomEmail(true);
        String fakePassword = new DataGenerators().generateRandomPassword(PASSWORD_MIN_LENGTH, PASSWORD_MAX_LENGTH);

        User user = new User("NameFakeNameFakeNameFakeNameFa", fakeEmail, fakePassword);
        var response= createUser(user);

        assertAll("Verify that user is able to register successfully with [Name] == 30 characters",

                () -> assertEquals(CREATED_STATUS.getStatus(), response.getStatus()),
                () -> assertEquals(REGISTRATION_SUCCESSFUL_MESSAGE.getMessage(), response.getMessage()),
                () -> assertEquals(EXPECTED_SUCCESS_TRUE, response.isSuccess()),
                () -> assertEquals(UUID_LENGTH, response.getData().getId().length(), "User [ID] is not correct"),
                () -> assertEquals(user.getName(), response.getData().getName(), "User [Name] is not correct"),
                () -> assertEquals(user.getEmail().toLowerCase(), response.getData().getEmail().toLowerCase(),"User [Email] is not correct")
        );
    }

    @DisplayName("Verify that user is able to register successfully with [Name] == 4 characters")
    @RepeatedTest(value = REPEAT_COUNT, name = "{displayName} : {currentRepetition}/{totalRepetitions}")
    void verifySuccessUserRegistrationWithNameEqualTo4Characters() {
        String fakeEmail = new DataGenerators().generateRandomEmail(true);
        String fakePassword = new DataGenerators().generateRandomPassword(PASSWORD_MIN_LENGTH, PASSWORD_MAX_LENGTH);

        User user = new User("Name", fakeEmail, fakePassword);
        var response= createUser(user);
        System.out.println("Fake email: " + fakeEmail);

        assertAll("Verify that user is able to register successfully with [Name] == 4 characters",
                    () -> assertEquals(UUID_LENGTH, response.getData().getId().length(), "User [ID] is not correct"),
                    () -> assertEquals(user.getName(), response.getData().getName(), "User [Name] is not correct"),
                    () -> assertEquals(user.getEmail().toLowerCase(), response.getData().getEmail().toLowerCase(),"User [Email] is not correct"),

                    () -> assertEquals(CREATED_STATUS.getStatus(), response.getStatus()),
                    () -> assertEquals(REGISTRATION_SUCCESSFUL_MESSAGE.getMessage(), response.getMessage()),
                    () -> assertEquals(EXPECTED_SUCCESS_TRUE, response.isSuccess())
        );
    }

    @DisplayName("Verify that user is NOT able to register successfully with existing [Email]")
    @RepeatedTest(value = REPEAT_COUNT, name = "{displayName} : {currentRepetition}/{totalRepetitions}")
    void verifyFailUserRegistrationWithEmailLessThan4Characters() {
        String fakeName = new DataGenerators().generateRandomName(NAME_MIN_LENGTH, NAME_MAX_LENGTH);

        User user = new User(fakeName, email, new DataGenerators().generateRandomPassword(6, 30));
        var response= createUser(user);

        assertAll("Verify that user is NOT able to register successfully with existing [Email]",
                    () -> assertEquals(CONFLICT_STATUS.getStatus(), response.getStatus(), "Incorrect status code"),
                    () -> assertEquals(EXISTING_EMAIL_MESSAGE.getMessage(), response.getMessage(), "Incorrect message"),
                    () -> assertEquals(EXPECTED_SUCCESS_FALSE, response.isSuccess(), "Incorrect success status")
        );
    }

    @DisplayName("Verify that user is able to Log In with valid credentials")
    @RepeatedTest(value = REPEAT_COUNT, name = "{displayName} : {currentRepetition}/{totalRepetitions}")
    void VerifySuccessfullLogin(){
        var response = logIn(email, password);

        assertAll("Verify that user is able to Log In with valid credentials",
                () -> assertEquals(UUID_LENGTH, response.getData().getId().length(), "User [ID] is not correct"),
                () -> assertEquals(email, response.getData().getEmail(), "User [Email] is not correct"),
                () -> assertEquals(TOKEN_LENGTH, response.getData().getToken().length(), "User [Token] is not correct" ),
                () -> assertEquals(SUCCESSFUL_STATUS.getStatus(), response.getStatus()),
                () -> assertEquals(LOGIN_SUCCESSFUL_MESSAGE.getMessage(), response.getMessage()),
                () -> assertEquals(EXPECTED_SUCCESS_TRUE, response.isSuccess())
        );
    }

}