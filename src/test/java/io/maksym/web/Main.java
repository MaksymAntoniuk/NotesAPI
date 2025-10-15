package io.maksym.web;

import io.maksym.web.base.BaseTest;
import io.maksym.web.util.DataGenerators;
import io.maksym.web.dto.User.User;
import io.restassured.RestAssured;
import org.junit.jupiter.api.*;
import com.github.javafaker.Faker;
import static io.maksym.web.enums.ErrorMessage.*;
import static io.maksym.web.enums.StatusCode.*;
import static io.maksym.web.util.Constants.*;


class LoginTests extends BaseTest {

    @DisplayName("Verify that user is able to register successfully")
    @RepeatedTest(value = REPEAT_COUNT, name = "{displayName} : {currentRepetition}/{totalRepetitions}")
    void verifySuccessUserRegistration() {

        String fakeEmail = new DataGenerators().generateRandomEmail(true);
        String fakeName = new DataGenerators().generateRandomName(NAME_MIN_LENGTH, NAME_MAX_LENGTH);
        String fakePassword = new DataGenerators().generateRandomPassword(PASSWORD_MIN_LENGTH, PASSWORD_MAX_LENGTH);

        System.out.println("Fake name: " + fakeName);
        System.out.println("Fake email: " + fakeEmail);

        User user = new User(fakeName, fakeEmail, fakePassword);

        var response= createUser(user);

        Assertions.assertAll("User Registration Response",
                () -> Assertions.assertEquals(UUID_LENGTH, response.getData().getId().length(), "User [ID] is not correct"),
                () -> Assertions.assertEquals(user.getName(), response.getData().getName(), "User [Name] is not correct"),
                () -> Assertions.assertEquals(user.getEmail().toLowerCase(), response.getData().getEmail().toLowerCase(),"User [Email] is not correct"),
                () -> Assertions.assertEquals(CREATED_STATUS.getStatus(), response.getStatus()),
                () -> Assertions.assertEquals(REGISTRATION_SUCCESSFUL_MESSAGE.getMessage(), response.getMessage()),
                () -> Assertions.assertEquals(EXPECTED_SUCCESS, response.isSuccess()));

        System.out.println(user);
    }


    @DisplayName("Verify that user is not able to register successfully with empty [Email]")
    @RepeatedTest(value = REPEAT_COUNT, name = "{displayName} : {currentRepetition}/{totalRepetitions}")
    void verifyFailUserRegistrationWithEmptyEmail() {
        User user = new User(new Faker().name().firstName(), "", new Faker().internet().password());
        var response= createUser(user);

        Assertions.assertEquals(BAD_REQUEST_STATUS.getStatus(), response.getStatus(), "Incorrect status code");
        Assertions.assertEquals(EMAIL_MISSED_MESSAGE.getMessage(), response.getMessage(), "Incorrect message");
        Assertions.assertEquals(EXPECTED_SUCCESS_FALSE, response.isSuccess(), "Incorrect success status");
        System.out.println(response);
    }

    @DisplayName("Verify that user is not able to register successfully with empty [Name]")
    @RepeatedTest(value = REPEAT_COUNT, name = "{displayName} : {currentRepetition}/{totalRepetitions}")
    void verifyFailUserRegistrationWithEmptyName() {
        String fakeEmail = new Faker().artist().name() + System.currentTimeMillis() + "@gmail.com";

        User user = new User("", fakeEmail, new Faker().internet().password());
        var response= createUser(user);

        Assertions.assertEquals(BAD_REQUEST_STATUS.getStatus(), response.getStatus(), "Incorrect status code");
        Assertions.assertEquals(NAME_MISSED_MESSAGE.getMessage(), response.getMessage(), "Incorrect message");
        Assertions.assertEquals(EXPECTED_SUCCESS_FALSE, response.isSuccess(), "Incorrect success status");
        System.out.println(response);
    }

    @RepeatedTest(value = REPEAT_COUNT, name = "{displayName} : {currentRepetition}/{totalRepetitions}")
    @DisplayName("Verify that user is not able to register successfully with empty [Password]")
    void verifyFailUserRegistrationWithEmptyPassword() {
        String fakeEmail = new DataGenerators().generateRandomEmail(true);
        String fakeName = new DataGenerators().generateRandomName(NAME_MIN_LENGTH, NAME_MAX_LENGTH);

        User user = new User(fakeName, fakeEmail, "");
        var response= createUser(user);

        Assertions.assertEquals(BAD_REQUEST_STATUS.getStatus(), response.getStatus(), "Incorrect status code");
        Assertions.assertEquals(PASSWORD_MISSED_MESSAGE.getMessage(), response.getMessage(), "Incorrect message");
        Assertions.assertEquals(EXPECTED_SUCCESS_FALSE, response.isSuccess(), "Incorrect success status");
        System.out.println(user);
        System.out.println(response);
    }

    @RepeatedTest(value = REPEAT_COUNT, name = "{displayName} : {currentRepetition}/{totalRepetitions}")
    @DisplayName("Verify that user is not able to register successfully with empty [All fields]")
    void verifyFailUserRegistrationWithEmptyAllFields() {
        User user = new User("", "", "");
        var response= createUser(user);

        Assertions.assertEquals(BAD_REQUEST_STATUS.getStatus(), response.getStatus(), "Incorrect status code");
        Assertions.assertEquals(NAME_MISSED_MESSAGE.getMessage(), response.getMessage(), "Incorrect message");
        Assertions.assertEquals(EXPECTED_SUCCESS_FALSE, response.isSuccess(), "Incorrect success status");
        System.out.println(response);
    }

    @DisplayName("Verify that user is NOT able to register successfully with [Name] < 4 characters")
    @RepeatedTest(value = REPEAT_COUNT, name = "{displayName} : {currentRepetition}/{totalRepetitions}")
    void verifyFailUserRegistrationWithNameLessThan4Characters() {
        String fakeEmail = new DataGenerators().generateRandomEmail(true);
        String fakeName = new DataGenerators().generateRandomName(1, 3);

        String fakePassword = new DataGenerators().generateRandomPassword(PASSWORD_MIN_LENGTH, PASSWORD_MAX_LENGTH);
        User user = new User(fakeName, fakeEmail, fakePassword);
        var response= createUser(user);

        System.out.println("Fake name: " + fakeName);
        System.out.println("Fake email: " + fakeEmail);

        Assertions.assertAll("User Registration Response",
                () -> Assertions.assertEquals(BAD_REQUEST_STATUS.getStatus(), response.getStatus(), "Incorrect status code"),
                () -> Assertions.assertEquals(NAME_MISSED_MESSAGE.getMessage(), response.getMessage(), "Incorrect message"),
                () -> Assertions.assertEquals(EXPECTED_SUCCESS_FALSE, response.isSuccess(), "Incorrect success status")
        );

        System.out.println(response);
        System.out.println(user);
    }
    @DisplayName("Verify that user is NOT able to register successfully with [Name] > 30 characters")
    @RepeatedTest(value = REPEAT_COUNT, name = "{displayName} : {currentRepetition}/{totalRepetitions}")
    void verifyFailUserRegistrationWithNameMoreThan4Characters() {
        String fakeEmail = new DataGenerators().generateRandomEmail(true);
        String fakeName = new DataGenerators().generateRandomName(31, 100);
        String fakePassword = new DataGenerators().generateRandomPassword(PASSWORD_MIN_LENGTH, PASSWORD_MAX_LENGTH);

        User user = new User(fakeName, fakeEmail, fakePassword);
        var response= createUser(user);

        System.out.println("Fake name: " + fakeName);
        System.out.println("Fake email: " + fakeEmail);

        Assertions.assertEquals(BAD_REQUEST_STATUS.getStatus(), response.getStatus(), "Incorrect status code");
        Assertions.assertEquals(NAME_MISSED_MESSAGE.getMessage(), response.getMessage(), "Incorrect message");
        Assertions.assertEquals(EXPECTED_SUCCESS_FALSE, response.isSuccess(), "Incorrect success status");
        System.out.println(response);
        System.out.println(user);
    }

    @DisplayName("Verify that user is NOT able to register successfully with [Null] in [Name]")
    @RepeatedTest(value = REPEAT_COUNT, name = "{displayName} : {currentRepetition}/{totalRepetitions}")
    void verifyFailUserRegistrationWithNullName() {
        String fakeEmail = new DataGenerators().generateRandomEmail(true);
        String fakeName = null;
        String fakePassword = new DataGenerators().generateRandomPassword(PASSWORD_MIN_LENGTH, PASSWORD_MAX_LENGTH);

        System.out.println("Fake name: " + fakeName);
        System.out.println("Fake email: " + fakeEmail);

        User user = new User(fakeName, fakeEmail, fakePassword);

        var response= createUser(user);

        Assertions.assertEquals(BAD_REQUEST_STATUS.getStatus(), response.getStatus(), "Incorrect status code");
        Assertions.assertEquals(NAME_MISSED_MESSAGE.getMessage(), response.getMessage(), "Incorrect message");
        Assertions.assertEquals(EXPECTED_SUCCESS_FALSE, response.isSuccess(), "Incorrect success status");
        System.out.println(response);
        System.out.println(user);
    }

    @DisplayName("Verify that user is able to register successfully with [Name] == 30 characters")
    @RepeatedTest(value = REPEAT_COUNT, name = "{displayName} : {currentRepetition}/{totalRepetitions}")
    void verifySuccessUserRegistrationWithNameEqualTo30Characters() {
        String fakeEmail = new DataGenerators().generateRandomEmail(true);
        String fakePassword = new DataGenerators().generateRandomPassword(PASSWORD_MIN_LENGTH, PASSWORD_MAX_LENGTH);

        User user = new User("NameFakeNameFakeNameFakeNameFa", fakeEmail, fakePassword);
        var response= createUser(user);
        System.out.println("Fake email: " + fakeEmail);

        Assertions.assertEquals(UUID_LENGTH, response.getData().getId().length(), "User [ID] is not correct");
        Assertions.assertEquals(user.getName(), response.getData().getName(), "User [Name] is not correct");
        Assertions.assertEquals(user.getEmail().toLowerCase(), response.getData().getEmail().toLowerCase(),"User [Email] is not correct");

        Assertions.assertEquals(CREATED_STATUS.getStatus(), response.getStatus());
        Assertions.assertEquals(REGISTRATION_SUCCESSFUL_MESSAGE.getMessage(), response.getMessage());
        Assertions.assertEquals(EXPECTED_SUCCESS, response.isSuccess());
        System.out.println(user);
    }

    @DisplayName("Verify that user is able to register successfully with [Name] == 4 characters")
    @RepeatedTest(value = REPEAT_COUNT, name = "{displayName} : {currentRepetition}/{totalRepetitions}")
    void verifySuccessUserRegistrationWithNameEqualTo4Characters() {
        String fakeEmail = new DataGenerators().generateRandomEmail(true);
        String fakePassword = new DataGenerators().generateRandomPassword(PASSWORD_MIN_LENGTH, PASSWORD_MAX_LENGTH);

        User user = new User("Name", fakeEmail, fakePassword);
        var response= createUser(user);
        System.out.println("Fake email: " + fakeEmail);


        Assertions.assertEquals(UUID_LENGTH, response.getData().getId().length(), "User [ID] is not correct");
        Assertions.assertEquals(user.getName(), response.getData().getName(), "User [Name] is not correct");
        Assertions.assertEquals(user.getEmail().toLowerCase(), response.getData().getEmail().toLowerCase(),"User [Email] is not correct");

        Assertions.assertEquals(CREATED_STATUS.getStatus(), response.getStatus());
        Assertions.assertEquals(REGISTRATION_SUCCESSFUL_MESSAGE.getMessage(), response.getMessage());
        Assertions.assertEquals(EXPECTED_SUCCESS, response.isSuccess());
        System.out.println(user);
    }

    @DisplayName("Verify that user is NOT able to register successfully with existing [Email]")
    @RepeatedTest(value = REPEAT_COUNT, name = "{displayName} : {currentRepetition}/{totalRepetitions}")
    void verifyFailUserRegistrationWithEmailLessThan4Characters() {
        String fakeName = new DataGenerators().generateRandomName(NAME_MIN_LENGTH, NAME_MAX_LENGTH);

        User user = new User(fakeName, email, new DataGenerators().generateRandomPassword(6, 30));
        var response= createUser(user);

        Assertions.assertEquals(CONFLICT_STATUS.getStatus(), response.getStatus(), "Incorrect status code");
        Assertions.assertEquals(EXISTING_EMAIL_MESSAGE.getMessage(), response.getMessage(), "Incorrect message");
        Assertions.assertEquals(EXPECTED_SUCCESS_FALSE, response.isSuccess(), "Incorrect success status");
        System.out.println(user);
        System.out.println(response);
    }

    @DisplayName("Verify that user is able to Log In with valid credentials")
    @RepeatedTest(value = REPEAT_COUNT, name = "{displayName} : {currentRepetition}/{totalRepetitions}")
    void VerifySuccessfullLogin(){
        var response = logIn(email, password);

        Assertions.assertEquals(UUID_LENGTH, response.getData().getId().length(), "User [ID] is not correct");
        Assertions.assertEquals(email, response.getData().getEmail(), "User [Email] is not correct");
        Assertions.assertEquals(TOKEN_LENGTH, response.getData().getToken().length(), "User [Token] is not correct" );
        Assertions.assertEquals(SUCCESSFUL_STATUS.getStatus(), response.getStatus());
        Assertions.assertEquals(LOGIN_SUCCESSFUL_MESSAGE.getMessage(), response.getMessage());
        Assertions.assertEquals(EXPECTED_SUCCESS, response.isSuccess());
        System.out.println(response);
    }


    @AfterEach
    public void tearDown(){
        RestAssured.reset();
    }
}