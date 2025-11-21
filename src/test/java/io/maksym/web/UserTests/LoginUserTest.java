package io.maksym.web.UserTests;

import io.maksym.web.Records.LoginBody;
import io.maksym.web.Records.UserBody;
import io.maksym.web.base.BaseTest;
import io.maksym.web.dto.Login.LoginResponse;
import io.maksym.web.dto.Registration.RegistrationSuccResponse.RegistrationSuccessfulResponse;
import io.maksym.web.requests.actions.SimpleAction;
import io.maksym.web.util.DataGenerators;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.maksym.web.enums.ErrorMessage.LOGIN_SUCCESSFUL_MESSAGE;
import static io.maksym.web.enums.StatusCode.SUCCESSFUL_STATUS;
import static io.maksym.web.util.Constants.*;
import static io.maksym.web.util.SchemaResponseValidator.assertResponseSchema;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Epic("User API")
@DisplayName("Verify that user is able to Log In with valid credentials")
@Severity(io.qameta.allure.SeverityLevel.CRITICAL)
public class LoginUserTest extends BaseTest {
    @DisplayName("Verify that user is able to Log In with valid credentials")
    @Description("""
            1. Enter valid value in [Name] field
            2. Enter valid value in [Email] field
            3. Enter valid value in [Password] field
            4. Send request
            """)
    @Test
    void VerifySuccessfullLogin(){
        String fakeName = new DataGenerators().generateRandomName(NAME_MIN_LENGTH, NAME_MAX_LENGTH);
        String fakeEmail = new DataGenerators().generateRandomEmail(true);
        String fakePassword = new DataGenerators().generateRandomPassword(PASSWORD_MIN_LENGTH, PASSWORD_MAX_LENGTH);

        UserBody user = new UserBody(fakeName, fakeEmail, fakePassword);
        RegistrationSuccessfulResponse registrationResponse = registerUser(user).as(RegistrationSuccessfulResponse.class);

        Response logInUser = SimpleAction.logInUser(new LoginBody(registrationResponse.getData().getEmail(), fakePassword));
        logInUser.getBody().jsonPath().
        boolean validationSchema = assertResponseSchema("login-response-schema.json", logInUser);

        LoginResponse response = logInUser.as(LoginResponse.class);

        assertAll("Verify that user is able to Log In with valid credentials",
                () -> assertTrue(validationSchema, "Incorrect response schema"),
                () -> assertEquals(UUID_LENGTH, response.getData().getId().length(), "User [ID] is not correct"),
                () -> assertEquals(fakeEmail, response.getData().getEmail(), "User [Email] is not correct"),
                () -> assertEquals(TOKEN_LENGTH, response.getData().getToken().length(), "User [Token] is not correct" ),
                () -> assertEquals(SUCCESSFUL_STATUS.getStatus(), response.getStatus()),
                () -> assertEquals(LOGIN_SUCCESSFUL_MESSAGE.getMessage(), response.getMessage()),
                () -> assertEquals(EXPECTED_SUCCESS_TRUE, response.isSuccess())
        );
        deleteUserProfile(response.getData().getToken());
    }
}
