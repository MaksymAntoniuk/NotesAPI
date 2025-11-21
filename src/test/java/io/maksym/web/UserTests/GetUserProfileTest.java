package io.maksym.web.UserTests;

import io.maksym.web.Records.LoginBody;
import io.maksym.web.Records.UserBody;
import io.maksym.web.base.BaseTest;
import io.maksym.web.dto.Login.LoginResponse;
import io.maksym.web.dto.Profile.ProfileResponse;
import io.maksym.web.dto.Registration.RegistrationSuccResponse.RegistrationSuccessfulResponse;
import io.maksym.web.requests.actions.SimpleAction;
import io.maksym.web.util.DataGenerators;
import io.maksym.web.util.SchemaResponseValidator;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;

import static io.maksym.web.enums.ErrorMessage.*;
import static io.maksym.web.enums.StatusCode.*;
import static io.maksym.web.util.Constants.*;
import static io.maksym.web.util.SchemaResponseValidator.assertResponseSchema;
import static org.junit.jupiter.api.Assertions.*;

@Epic("User API")
@DisplayName("Verify that user is able to fetch [Profile] data")
@io.qameta.allure.Severity(io.qameta.allure.SeverityLevel.CRITICAL)
public class GetUserProfileTest extends BaseTest {

    @RepeatedTest(value = REPEAT_COUNT, name = "{displayName} : {currentRepetition}/{totalRepetitions}")
    @DisplayName("Verify that user is able to fetch [Profile] data")
    @Description("""
            1. Register User
            2. Log In with User
            3. Get user Profile
            4. Assert response
            """)
    public void getUserProfileTest(){
        String fakeName = new DataGenerators().generateRandomName(NAME_MIN_LENGTH, NAME_MAX_LENGTH);
        String fakeEmail = new DataGenerators().generateRandomEmail(true);
        String fakePassword = new DataGenerators().generateRandomPassword(PASSWORD_MIN_LENGTH, PASSWORD_MAX_LENGTH);

        Response registerResponse = registerUser(new UserBody(fakeName, fakeEmail, fakePassword));
        boolean validationSchemaOfFirstRegisterResponse = assertResponseSchema("registration-response-schema.json", registerResponse);

        RegistrationSuccessfulResponse response = registerResponse.as(RegistrationSuccessfulResponse.class);

        assertAll("Verify first successfull Registration ",
                () -> assertTrue(validationSchemaOfFirstRegisterResponse, "Incorrect response schema"),
                () -> assertEquals(CREATED_STATUS.getStatus(), response.getStatus(), "Incorrect status code"),

                () -> assertEquals(REGISTRATION_SUCCESSFUL_MESSAGE.getMessage(), response.getMessage()),
                () -> assertEquals(EXPECTED_SUCCESS_TRUE, response.isSuccess())
        );

       Response responseLogin = SimpleAction.logInUser(new LoginBody(fakeEmail, fakePassword));
       boolean responseLoginSchema = assertResponseSchema("login-response-schema.json", responseLogin);
       LoginResponse responseLoginData = responseLogin.as(LoginResponse.class);
       String id = responseLoginData.getData().getId();
       String token = responseLoginData.getData().getToken();
       String email = responseLoginData.getData().getEmail();
       String name = responseLoginData.getData().getName();


        Assertions.assertAll("Verify that user is able to fetch [Profile] data",
                () -> assertTrue(responseLoginSchema, "Incorrect response schema"),
                () -> assertEquals(SUCCESSFUL_STATUS.getStatus(), responseLoginData.getStatus(), "Incorrect status code"),
                () -> assertEquals(EXPECTED_SUCCESS_TRUE, responseLoginData.isSuccess(), "Incorrect success status"),
                () -> assertEquals(LOGIN_SUCCESSFUL_MESSAGE.getMessage(), responseLoginData.getMessage()),

                () -> assertEquals(email, responseLoginData.getData().getEmail(), "Incorrect [Email]"),
                () -> assertEquals(name, responseLoginData.getData().getName(), "Incorrect [Name]"),
                () -> assertEquals(id, responseLoginData.getData().getId(), "Incorrect [Id]")
        );
    }
    @Description("""
            1. Attempt to get User Profile with Invalid Token
            2. Assert response
            """)
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
