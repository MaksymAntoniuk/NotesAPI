package io.maksym.web;


import io.maksym.web.base.BaseTest;
import io.maksym.web.dto.HealthCheck.HealthCheckResponse;
import io.maksym.web.util.DataGenerators;
import io.maksym.web.dto.User.User;
import io.maksym.web.actions.SimpleAction;
import io.restassured.RestAssured;

import org.junit.jupiter.api.*;

import com.github.javafaker.Faker;

import static io.maksym.web.enums.ErrorMessage.*;
import static io.maksym.web.enums.StatusCode.*;
import static io.maksym.web.validation.ValidationConstants.*;


class Main extends BaseTest {
    
    @RepeatedTest(value = REPEAT_COUNT, name = "{displayName} : {currentRepetition}/{totalRepetitions}")
    @DisplayName("Verify Successfull Health Check")
    void VerifySuccessfullHealthCheck() {
         final boolean EXPECTED_SUCCESS = true;

        HealthCheckResponse response = new SimpleAction().checkHealth();
        System.out.println(response);

        Assertions.assertAll("Health Check Response",
                ()->Assertions.assertEquals(SUCCESSFUL_STATUS.getStatus(), response.getStatus(), "Incorrect status code"),
                ()->Assertions.assertEquals(HEALTH_CHECK_MESSAGE.getMessage(), response.getMessage(), "Incorrect message"),
                ()->Assertions.assertEquals(EXPECTED_SUCCESS, response.isSuccess(), "Incorrect success status")
        );
    }

    @DisplayName("Verify that user is able to register successfully")
    @RepeatedTest(value = REPEAT_COUNT, name = "{displayName} : {currentRepetition}/{totalRepetitions}")
    void verifySuccessUserRegistration() {
        final Boolean EXPECTED_SUCCESS = true;

        String fakeEmail = new DataGenerators().generateRandomEmail(true);
        String fakeName = new DataGenerators().generateRandomName(NAME_MIN_LENGTH, NAME_MAX_LENGTH);
        String fakePassword = new DataGenerators().generateRandomPassword(PASSWORD_MIN_LENGTH, PASSWORD_MAX_LENGTH);

        System.out.println("Fake name: " + fakeName);
        System.out.println("Fake email: " + fakeEmail);

        User user = new User(fakeName, fakeEmail, fakePassword);

        var response= new SimpleAction().createUser(user);

        Assertions.assertEquals(UUID_LENGTH, response.getData().getId().length(), "User [ID] is not correct");
        Assertions.assertEquals(user.getName(), response.getData().getName(), "User [Name] is not correct");
        Assertions.assertEquals(user.getEmail().toLowerCase(), response.getData().getEmail().toLowerCase(),"User [Email] is not correct");

        Assertions.assertEquals(CREATED_STATUS.getStatus(), response.getStatus());
        Assertions.assertEquals(REGISTRATION_SUCCESSFUL_MESSAGE.getMessage(), response.getMessage());
        Assertions.assertEquals(EXPECTED_SUCCESS, response.isSuccess());
        System.out.println(user);
    }


    @DisplayName("Verify that user is not able to register successfully with empty [Email]")
    @RepeatedTest(value = REPEAT_COUNT, name = "{displayName} : {currentRepetition}/{totalRepetitions}")
    void verifyFailUserRegistrationWithEmptyEmail() {
        final Boolean EXPECTED_SUCCESS = false;

        User user = new User(new Faker().name().firstName(), "", new Faker().internet().password());
        var response= new SimpleAction().createUser(user);
        Assertions.assertEquals(BAD_REQUEST_STATUS.getStatus(), response.getStatus(), "Incorrect status code");
        Assertions.assertEquals(EMAIL_MISSED_MESSAGE.getMessage(), response.getMessage(), "Incorrect message");
        Assertions.assertEquals(EXPECTED_SUCCESS, response.isSuccess(), "Incorrect success status");
        System.out.println(response);
    }

    @DisplayName("Verify that user is not able to register successfully with empty [Name]")
    @RepeatedTest(value = REPEAT_COUNT, name = "{displayName} : {currentRepetition}/{totalRepetitions}")
    void verifyFailUserRegistrationWithEmptyName() {
        final Boolean EXPECTED_SUCCESS = false;

        String fakeEmail = new Faker().artist().name() + System.currentTimeMillis() + "@gmail.com";

        User user = new User("", fakeEmail, new Faker().internet().password());
        var response= new SimpleAction().createUser(user);

        Assertions.assertEquals(BAD_REQUEST_STATUS.getStatus(), response.getStatus(), "Incorrect status code");
        Assertions.assertEquals(NAME_MISSED_MESSAGE.getMessage(), response.getMessage(), "Incorrect message");
        Assertions.assertEquals(EXPECTED_SUCCESS, response.isSuccess(), "Incorrect success status");
        System.out.println(response);
    }

    @RepeatedTest(value = REPEAT_COUNT, name = "{displayName} : {currentRepetition}/{totalRepetitions}")
    @DisplayName("Verify that user is not able to register successfully with empty [Password]")
    void verifyFailUserRegistrationWithEmptyPassword() {
        final Boolean EXPECTED_SUCCESS = false;

        String fakeEmail = new DataGenerators().generateRandomEmail(true);
        String fakeName = new DataGenerators().generateRandomName(NAME_MIN_LENGTH, NAME_MAX_LENGTH);

        User user = new User(fakeName, fakeEmail, "");
        var response= new SimpleAction().createUser(user);

        Assertions.assertEquals(BAD_REQUEST_STATUS.getStatus(), response.getStatus(), "Incorrect status code");
        Assertions.assertEquals(PASSWORD_MISSED_MESSAGE.getMessage(), response.getMessage(), "Incorrect message");
        Assertions.assertEquals(EXPECTED_SUCCESS, response.isSuccess(), "Incorrect success status");
        System.out.println(user);
        System.out.println(response);
    }

    @RepeatedTest(value = REPEAT_COUNT, name = "{displayName} : {currentRepetition}/{totalRepetitions}")
    @DisplayName("Verify that user is not able to register successfully with empty [All fields]")
    void verifyFailUserRegistrationWithEmptyAllFields() {
        final Boolean EXPECTED_SUCCESS = false;

        User user = new User("", "", "");
        var response= new SimpleAction().createUser(user);

        Assertions.assertEquals(BAD_REQUEST_STATUS.getStatus(), response.getStatus(), "Incorrect status code");
        Assertions.assertEquals(NAME_MISSED_MESSAGE.getMessage(), response.getMessage(), "Incorrect message");
        Assertions.assertEquals(EXPECTED_SUCCESS, response.isSuccess(), "Incorrect success status");
        System.out.println(response);
    }

    @DisplayName("Verify that user is NOT able to register successfully with [Name] < 4 characters")
    @RepeatedTest(value = REPEAT_COUNT, name = "{displayName} : {currentRepetition}/{totalRepetitions}")
    void verifyFailUserRegistrationWithNameLessThan4Characters() {
        final Boolean EXPECTED_SUCCESS = false;

        String fakeEmail = new DataGenerators().generateRandomEmail(true);
        String fakeName = new DataGenerators().generateRandomName(1, 3);
        String fakePassword = new DataGenerators().generateRandomPassword(PASSWORD_MIN_LENGTH, PASSWORD_MAX_LENGTH);

        System.out.println("Fake name: " + fakeName);
        System.out.println("Fake email: " + fakeEmail);


        User user = new User(fakeName, fakeEmail, fakePassword);

        var response= new SimpleAction().createUser(user);

        Assertions.assertEquals(BAD_REQUEST_STATUS.getStatus(), response.getStatus(), "Incorrect status code");
        Assertions.assertEquals(NAME_MISSED_MESSAGE.getMessage(), response.getMessage(), "Incorrect message");
        Assertions.assertEquals(EXPECTED_SUCCESS, response.isSuccess(), "Incorrect success status");
        System.out.println(response);
        System.out.println(user);
    }
    @DisplayName("Verify that user is NOT able to register successfully with [Name] > 30 characters")
    @RepeatedTest(value = REPEAT_COUNT, name = "{displayName} : {currentRepetition}/{totalRepetitions}")
    void verifyFailUserRegistrationWithNameMoreThan4Characters() {
        final Boolean EXPECTED_SUCCESS = false;

        String fakeEmail = new DataGenerators().generateRandomEmail(true);
        String fakeName = new DataGenerators().generateRandomName(31, 100);
        String fakePassword = new DataGenerators().generateRandomPassword(PASSWORD_MIN_LENGTH, PASSWORD_MAX_LENGTH);

        System.out.println("Fake name: " + fakeName);
        System.out.println("Fake email: " + fakeEmail);

        User user = new User(fakeName, fakeEmail, fakePassword);

        var response= new SimpleAction().createUser(user);

        Assertions.assertEquals(BAD_REQUEST_STATUS.getStatus(), response.getStatus(), "Incorrect status code");
        Assertions.assertEquals(NAME_MISSED_MESSAGE.getMessage(), response.getMessage(), "Incorrect message");
        Assertions.assertEquals(EXPECTED_SUCCESS, response.isSuccess(), "Incorrect success status");
        System.out.println(response);
        System.out.println(user);
    }

    @DisplayName("Verify that user is NOT able to register successfully with [Null] in [Name]")
    @RepeatedTest(value = REPEAT_COUNT, name = "{displayName} : {currentRepetition}/{totalRepetitions}")
    void verifyFailUserRegistrationWithNullName() {
        final Boolean EXPECTED_SUCCESS = false;

        String fakeEmail = new DataGenerators().generateRandomEmail(true);
        String fakeName = null;
        String fakePassword = new DataGenerators().generateRandomPassword(PASSWORD_MIN_LENGTH, PASSWORD_MAX_LENGTH);

        System.out.println("Fake name: " + fakeName);
        System.out.println("Fake email: " + fakeEmail);

        User user = new User(fakeName, fakeEmail, fakePassword);

        var response= new SimpleAction().createUser(user);

        Assertions.assertEquals(BAD_REQUEST_STATUS.getStatus(), response.getStatus(), "Incorrect status code");
        Assertions.assertEquals(NAME_MISSED_MESSAGE.getMessage(), response.getMessage(), "Incorrect message");
        Assertions.assertEquals(EXPECTED_SUCCESS, response.isSuccess(), "Incorrect success status");
        System.out.println(response);
        System.out.println(user);
    }

    @DisplayName("Verify that user is able to register successfully with [Name] == 30 characters")
    @RepeatedTest(value = REPEAT_COUNT, name = "{displayName} : {currentRepetition}/{totalRepetitions}")
    void verifySuccessUserRegistrationWithNameEqualTo30Characters() {
        final Boolean EXPECTED_SUCCESS = true;

        String fakeEmail = new DataGenerators().generateRandomEmail(true);
        String fakePassword = new DataGenerators().generateRandomPassword(PASSWORD_MIN_LENGTH, PASSWORD_MAX_LENGTH);

        System.out.println("Fake email: " + fakeEmail);

        User user = new User("NameFakeNameFakeNameFakeNameFa", fakeEmail, fakePassword);

        var response= new SimpleAction().createUser(user);

        Assertions.assertNotNull(response.getData(), "User [Data] is null");
        Assertions.assertNotNull(response.getData().getId(), "User [ID] is null");
        Assertions.assertNotNull(response.getData().getName(), "User [Name] is null");
        Assertions.assertNotNull(response.getData().getEmail(), "User [Email] is null");

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
        final Boolean EXPECTED_SUCCESS = true;

        String fakeEmail = new DataGenerators().generateRandomEmail(true);
        String fakePassword = new DataGenerators().generateRandomPassword(PASSWORD_MIN_LENGTH, PASSWORD_MAX_LENGTH);

        System.out.println("Fake email: " + fakeEmail);

        User user = new User("Name", fakeEmail, fakePassword);

        var response= new SimpleAction().createUser(user);

        Assertions.assertNotNull(response.getData(), "User [Data] is null");
        Assertions.assertNotNull(response.getData().getId(), "User [ID] is null");
        Assertions.assertNotNull(response.getData().getName(), "User [Name] is null");
        Assertions.assertNotNull(response.getData().getEmail(), "User [Email] is null");

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
        final Boolean EXPECTED_SUCCESS = false;

        String fakeName = new DataGenerators().generateRandomName(NAME_MIN_LENGTH, NAME_MAX_LENGTH);

        User user = new User(fakeName, email, new DataGenerators().generateRandomPassword(6, 30));
        var response= new SimpleAction().createUser(user);

        Assertions.assertEquals(CONFLICT_STATUS.getStatus(), response.getStatus(), "Incorrect status code");
        Assertions.assertEquals(EXISTING_EMAIL_MESSAGE.getMessage(), response.getMessage(), "Incorrect message");
        Assertions.assertEquals(EXPECTED_SUCCESS, response.isSuccess(), "Incorrect success status");
        System.out.println(user);
        System.out.println(response);
    }

    @DisplayName("Verify that user is able to Log In with valid credentials")
    @RepeatedTest(value = REPEAT_COUNT, name = "{displayName} : {currentRepetition}/{totalRepetitions}")
    void VerifySuccessfullLogin(){
        final Boolean EXPECTED_SUCCESS = true;

        var response = new SimpleAction().logIn(email, password);

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