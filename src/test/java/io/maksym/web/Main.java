package io.maksym.web;


import io.maksym.web.DTO.HealthCheck.HealthCheckResponse;
import io.maksym.web.Helpers.DataGenerators;
import io.maksym.web.DTO.User.User;
import io.maksym.web.Service.ServiceRequest;
import io.restassured.RestAssured;

import org.junit.jupiter.api.*;

import com.github.javafaker.Faker;


class Main {

    static final int repeatCount = 1;
    String email = "<EMAIL>";
    String password = "<PASSWORD>";
    @BeforeEach
    public void setup(){
        String baseUrl = "https://practice.expandtesting.com/notes/api/";
        RestAssured.baseURI = baseUrl;


        ServiceRequest serviceRequest = new ServiceRequest();
        String passwordCred = new DataGenerators().generateRandomPassword(6, 30);
        var createdUser = serviceRequest.createUser(new User(new DataGenerators().generateValidRandomName(4, 30), new DataGenerators().generateRandomEmail(true), passwordCred));
        email = createdUser.getData().getEmail();
        password = passwordCred;
    }

    @RepeatedTest(value = repeatCount, name = "{displayName} : {currentRepetition}/{totalRepetitions}")
    @DisplayName("Verify Successfull Health Check")
    void VerifySuccessfullHealthCheck() {
         final int EXPECTED_STATUS = 200;
         final String EXPECTED_MESSAGE = "Notes API is Running";
         final boolean EXPECTED_SUCCESS = true;

        HealthCheckResponse response = new ServiceRequest().checkHealth();
        System.out.println(response);

        Assertions.assertAll("Health Check Response",
                ()->Assertions.assertEquals(EXPECTED_STATUS, response.getStatus(), "Incorrect status code"),
                ()->Assertions.assertEquals(EXPECTED_MESSAGE, response.getMessage(), "Incorrect message"),
                ()->Assertions.assertEquals(EXPECTED_SUCCESS, response.isSuccess(), "Incorrect success status")
        );
    }

    @DisplayName("Verify that user is able to register successfully")
    @RepeatedTest(value = repeatCount, name = "{displayName} : {currentRepetition}/{totalRepetitions}")
    void verifySuccessUserRegistration() {
        final int EXPECTED_STATUS = 201;
        final String EXPECTED_MESSAGE = "User account created successfully";
        final Boolean EXPECTED_SUCCESS = true;

        String fakeEmail = new DataGenerators().generateRandomEmail(true);
        String fakeName = new DataGenerators().generateValidRandomName(4, 30);
        String fakePassword = new DataGenerators().generateRandomPassword(6, 30);

        System.out.println("Fake name: " + fakeName);
        System.out.println("Fake email: " + fakeEmail);


        User user = new User(fakeName, fakeEmail, fakePassword);

        var response= new ServiceRequest().createUser(user);

        Assertions.assertNotNull(response.getData(), "User [Data] is null");
        Assertions.assertNotNull(response.getData().getId(), "User [ID] is null");
        Assertions.assertNotNull(response.getData().getName(), "User [Name] is null");
        Assertions.assertNotNull(response.getData().getEmail(), "User [Email] is null");

        Assertions.assertEquals(24, response.getData().getId().length(), "User [ID] is not correct");
        Assertions.assertEquals(user.getName(), response.getData().getName(), "User [Name] is not correct");
        Assertions.assertEquals(user.getEmail().toLowerCase(), response.getData().getEmail().toLowerCase(),"User [Email] is not correct");

        Assertions.assertEquals(EXPECTED_STATUS, response.getStatus());
        Assertions.assertEquals(EXPECTED_MESSAGE, response.getMessage());
        Assertions.assertEquals(EXPECTED_SUCCESS, response.isSuccess());
        System.out.println(user);
    }


    @DisplayName("Verify that user is not able to register successfully with empty [Email]")
    @RepeatedTest(value = repeatCount, name = "{displayName} : {currentRepetition}/{totalRepetitions}")
    void verifyFailUserRegistrationWithEmptyEmail() {
        final int EXPECTED_STATUS = 400;
        final String EXPECTED_MESSAGE = "A valid email address is required";
        final Boolean EXPECTED_SUCCESS = false;

        User user = new User(new Faker().name().firstName(), "", new Faker().internet().password());
        var response= new ServiceRequest().createUser(user);
        Assertions.assertEquals(EXPECTED_STATUS, response.getStatus(), "Incorrect status code");
        Assertions.assertEquals(EXPECTED_MESSAGE, response.getMessage(), "Incorrect message");
        Assertions.assertEquals(EXPECTED_SUCCESS, response.isSuccess(), "Incorrect success status");
        System.out.println(response);
    }

    @DisplayName("Verify that user is not able to register successfully with empty [Name]")
    @RepeatedTest(value = repeatCount, name = "{displayName} : {currentRepetition}/{totalRepetitions}")
    void verifyFailUserRegistrationWithEmptyName() {
        final int EXPECTED_STATUS = 400;
        final String EXPECTED_MESSAGE = "User name must be between 4 and 30 characters";
        final Boolean EXPECTED_SUCCESS = false;

        String fakeEmail = new Faker().artist().name() + System.currentTimeMillis() + "@gmail.com";

        User user = new User("", fakeEmail, new Faker().internet().password());
        var response= new ServiceRequest().createUser(user);

        Assertions.assertEquals(EXPECTED_STATUS, response.getStatus(), "Incorrect status code");
        Assertions.assertEquals(EXPECTED_MESSAGE, response.getMessage(), "Incorrect message");
        Assertions.assertEquals(EXPECTED_SUCCESS, response.isSuccess(), "Incorrect success status");
        System.out.println(response);
    }

    @RepeatedTest(value = repeatCount, name = "{displayName} : {currentRepetition}/{totalRepetitions}")
    @DisplayName("Verify that user is not able to register successfully with empty [Password]")
    void verifyFailUserRegistrationWithEmptyPassword() {
        final int EXPECTED_STATUS = 400;
        final String EXPECTED_MESSAGE = "Password must be between 6 and 30 characters";
        final Boolean EXPECTED_SUCCESS = false;

        String fakeEmail = new DataGenerators().generateRandomEmail(true);
        String fakeName = new DataGenerators().generateValidRandomName(4, 30);

        User user = new User(fakeName, fakeEmail, "");
        var response= new ServiceRequest().createUser(user);

        Assertions.assertEquals(EXPECTED_STATUS, response.getStatus(), "Incorrect status code");
        Assertions.assertEquals(EXPECTED_MESSAGE, response.getMessage(), "Incorrect message");
        Assertions.assertEquals(EXPECTED_SUCCESS, response.isSuccess(), "Incorrect success status");
        System.out.println(user);
        System.out.println(response);
    }

    @RepeatedTest(value = repeatCount, name = "{displayName} : {currentRepetition}/{totalRepetitions}")
    @DisplayName("Verify that user is not able to register successfully with empty [All fields]")
    void verifyFailUserRegistrationWithEmptyAllFields() {
        final int EXPECTED_STATUS = 400;
        final String EXPECTED_MESSAGE = "User name must be between 4 and 30 characters";
        final Boolean EXPECTED_SUCCESS = false;

        User user = new User("", "", "");
        var response= new ServiceRequest().createUser(user);

        Assertions.assertEquals(EXPECTED_STATUS, response.getStatus(), "Incorrect status code");
        Assertions.assertEquals(EXPECTED_MESSAGE, response.getMessage(), "Incorrect message");
        Assertions.assertEquals(EXPECTED_SUCCESS, response.isSuccess(), "Incorrect success status");
        System.out.println(response);
    }

    @DisplayName("Verify that user is NOT able to register successfully with [Name] < 4 characters")
    @RepeatedTest(value = repeatCount, name = "{displayName} : {currentRepetition}/{totalRepetitions}")
    void verifyFailUserRegistrationWithNameLessThan4Characters() {
        final int EXPECTED_STATUS = 400;
        final String EXPECTED_MESSAGE = "User name must be between 4 and 30 characters";
        final Boolean EXPECTED_SUCCESS = false;

        String fakeEmail = new DataGenerators().generateRandomEmail(true);
        String fakeName = new DataGenerators().generateValidRandomName(1, 3);
        String fakePassword = new DataGenerators().generateRandomPassword(6, 30);

        System.out.println("Fake name: " + fakeName);
        System.out.println("Fake email: " + fakeEmail);


        User user = new User(fakeName, fakeEmail, fakePassword);

        var response= new ServiceRequest().createUser(user);

        Assertions.assertEquals(EXPECTED_STATUS, response.getStatus(), "Incorrect status code");
        Assertions.assertEquals(EXPECTED_MESSAGE, response.getMessage(), "Incorrect message");
        Assertions.assertEquals(EXPECTED_SUCCESS, response.isSuccess(), "Incorrect success status");
        System.out.println(response);
        System.out.println(user);
    }
    @DisplayName("Verify that user is NOT able to register successfully with [Name] > 30 characters")
    @RepeatedTest(value = repeatCount, name = "{displayName} : {currentRepetition}/{totalRepetitions}")
    void verifyFailUserRegistrationWithNameMoreThan4Characters() {
        final int EXPECTED_STATUS = 400;
        final String EXPECTED_MESSAGE = "User name must be between 4 and 30 characters";
        final Boolean EXPECTED_SUCCESS = false;

        String fakeEmail = new DataGenerators().generateRandomEmail(true);
        String fakeName = new DataGenerators().generateValidRandomName(31, 100);
        String fakePassword = new DataGenerators().generateRandomPassword(6, 30);

        System.out.println("Fake name: " + fakeName);
        System.out.println("Fake email: " + fakeEmail);

        User user = new User(fakeName, fakeEmail, fakePassword);

        var response= new ServiceRequest().createUser(user);

        Assertions.assertEquals(EXPECTED_STATUS, response.getStatus(), "Incorrect status code");
        Assertions.assertEquals(EXPECTED_MESSAGE, response.getMessage(), "Incorrect message");
        Assertions.assertEquals(EXPECTED_SUCCESS, response.isSuccess(), "Incorrect success status");
        System.out.println(response);
        System.out.println(user);
    }

    @DisplayName("Verify that user is NOT able to register successfully with [Null] in [Name]")
    @RepeatedTest(value = repeatCount, name = "{displayName} : {currentRepetition}/{totalRepetitions}")
    void verifyFailUserRegistrationWithNullName() {
        final int EXPECTED_STATUS = 400;
        final String EXPECTED_MESSAGE = "User name must be between 4 and 30 characters";
        final Boolean EXPECTED_SUCCESS = false;

        String fakeEmail = new DataGenerators().generateRandomEmail(true);
        String fakeName = null;
        String fakePassword = new DataGenerators().generateRandomPassword(6, 30);

        System.out.println("Fake name: " + fakeName);
        System.out.println("Fake email: " + fakeEmail);

        User user = new User(fakeName, fakeEmail, fakePassword);

        var response= new ServiceRequest().createUser(user);

        Assertions.assertEquals(EXPECTED_STATUS, response.getStatus(), "Incorrect status code");
        Assertions.assertEquals(EXPECTED_MESSAGE, response.getMessage(), "Incorrect message");
        Assertions.assertEquals(EXPECTED_SUCCESS, response.isSuccess(), "Incorrect success status");
        System.out.println(response);
        System.out.println(user);
    }

    @DisplayName("Verify that user is able to register successfully with [Name] == 30 characters")
    @RepeatedTest(value = repeatCount, name = "{displayName} : {currentRepetition}/{totalRepetitions}")
    void verifySuccessUserRegistrationWithNameEqualTo30Characters() {
        final int EXPECTED_STATUS = 201;
        final String EXPECTED_MESSAGE = "User account created successfully";
        final Boolean EXPECTED_SUCCESS = true;

        String fakeEmail = new DataGenerators().generateRandomEmail(true);
        String fakePassword = new DataGenerators().generateRandomPassword(6, 30);

        System.out.println("Fake email: " + fakeEmail);

        User user = new User("NameFakeNameFakeNameFakeNameFa", fakeEmail, fakePassword);

        var response= new ServiceRequest().createUser(user);

        Assertions.assertNotNull(response.getData(), "User [Data] is null");
        Assertions.assertNotNull(response.getData().getId(), "User [ID] is null");
        Assertions.assertNotNull(response.getData().getName(), "User [Name] is null");
        Assertions.assertNotNull(response.getData().getEmail(), "User [Email] is null");

        Assertions.assertEquals(24, response.getData().getId().length(), "User [ID] is not correct");
        Assertions.assertEquals(user.getName(), response.getData().getName(), "User [Name] is not correct");
        Assertions.assertEquals(user.getEmail().toLowerCase(), response.getData().getEmail().toLowerCase(),"User [Email] is not correct");

        Assertions.assertEquals(EXPECTED_STATUS, response.getStatus());
        Assertions.assertEquals(EXPECTED_MESSAGE, response.getMessage());
        Assertions.assertEquals(EXPECTED_SUCCESS, response.isSuccess());
        System.out.println(user);
    }

    @DisplayName("Verify that user is able to register successfully with [Name] == 4 characters")
    @RepeatedTest(value = repeatCount, name = "{displayName} : {currentRepetition}/{totalRepetitions}")
    void verifySuccessUserRegistrationWithNameEqualTo4Characters() {
        final int EXPECTED_STATUS = 201;
        final String EXPECTED_MESSAGE = "User account created successfully";
        final Boolean EXPECTED_SUCCESS = true;

        String fakeEmail = new DataGenerators().generateRandomEmail(true);
        String fakePassword = new DataGenerators().generateRandomPassword(6, 30);

        System.out.println("Fake email: " + fakeEmail);

        User user = new User("Name", fakeEmail, fakePassword);

        var response= new ServiceRequest().createUser(user);

        Assertions.assertNotNull(response.getData(), "User [Data] is null");
        Assertions.assertNotNull(response.getData().getId(), "User [ID] is null");
        Assertions.assertNotNull(response.getData().getName(), "User [Name] is null");
        Assertions.assertNotNull(response.getData().getEmail(), "User [Email] is null");

        Assertions.assertEquals(24, response.getData().getId().length(), "User [ID] is not correct");
        Assertions.assertEquals(user.getName(), response.getData().getName(), "User [Name] is not correct");
        Assertions.assertEquals(user.getEmail().toLowerCase(), response.getData().getEmail().toLowerCase(),"User [Email] is not correct");

        Assertions.assertEquals(EXPECTED_STATUS, response.getStatus());
        Assertions.assertEquals(EXPECTED_MESSAGE, response.getMessage());
        Assertions.assertEquals(EXPECTED_SUCCESS, response.isSuccess());
        System.out.println(user);
    }
    @DisplayName("Verify that user is NOT able to register successfully with existing [Email]")
    @RepeatedTest(value = repeatCount, name = "{displayName} : {currentRepetition}/{totalRepetitions}")
    void verifyFailUserRegistrationWithEmailLessThan4Characters() {
        final int EXPECTED_STATUS = 409;
        final String EXPECTED_MESSAGE = "An account already exists with the same email address";
        final Boolean EXPECTED_SUCCESS = false;

        String fakeName = new DataGenerators().generateValidRandomName(4, 30);

        User user = new User(fakeName, email, new DataGenerators().generateRandomPassword(6, 30));
        var response= new ServiceRequest().createUser(user);

        Assertions.assertEquals(EXPECTED_STATUS, response.getStatus(), "Incorrect status code");
        Assertions.assertEquals(EXPECTED_MESSAGE, response.getMessage(), "Incorrect message");
        Assertions.assertEquals(EXPECTED_SUCCESS, response.isSuccess(), "Incorrect success status");
        System.out.println(user);
        System.out.println(response);
    }

    @DisplayName("Verify that user is able to Log In with valid credentials")
    @RepeatedTest(value = repeatCount, name = "{displayName} : {currentRepetition}/{totalRepetitions}")
    void VerifySuccessfullLogin(){
        final int EXPECTED_STATUS = 200;
        final String EXPECTED_MESSAGE = "Login successful";
        final Boolean EXPECTED_SUCCESS = true;

        var response = new ServiceRequest().LogInWithUser(email, password);
        Assertions.assertNotNull(response.getData(), "User [Data] is null");
        Assertions.assertNotNull(response.getData().getId(), "User [ID] is null");
        Assertions.assertNotNull(response.getData().getName(), "User [Name] is null");
        Assertions.assertNotNull(response.getData().getEmail(), "User [Email] is null");
        Assertions.assertEquals(24, response.getData().getId().length(), "User [ID] is not correct");
        Assertions.assertEquals(email, response.getData().getEmail(), "User [Email] is not correct");
        Assertions.assertEquals(64, response.getData().getToken().length(), "User [Token] is not correct" );

        Assertions.assertEquals(EXPECTED_STATUS, response.getStatus());
        Assertions.assertEquals(EXPECTED_MESSAGE, response.getMessage());
        Assertions.assertEquals(EXPECTED_SUCCESS, response.isSuccess());
        System.out.println(response);
    }



    @AfterEach
    public void tearDown(){
        RestAssured.reset();
    }


}