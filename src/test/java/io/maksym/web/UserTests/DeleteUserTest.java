package io.maksym.web.UserTests;

import io.maksym.web.Records.LoginBody;
import io.maksym.web.Records.UserBody;
import io.maksym.web.requests.actions.SimpleAction;
import io.maksym.web.base.BaseTest;
import io.maksym.web.dto.HealthCheck.BaseResponse;
import io.maksym.web.util.DataGenerators;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static io.maksym.web.enums.ErrorMessage.SUCCESSFUL_DELETION_MESSAGE;
import static io.maksym.web.enums.ErrorMessage.UNAUTHORIZED_MESSAGE;
import static io.maksym.web.enums.StatusCode.SUCCESSFUL_STATUS;
import static io.maksym.web.enums.StatusCode.UNAUTHORIZED_STATUS;
import static io.maksym.web.util.Constants.*;
import static io.maksym.web.util.SchemaResponseValidator.assertResponseSchema;
import static org.junit.jupiter.api.Assertions.*;

@Epic("User API")
@io.qameta.allure.Severity(io.qameta.allure.SeverityLevel.NORMAL)
@DisplayName("Verify that user is Deleted successfully")
public class DeleteUserTest extends BaseTest {
    @DisplayName("Verify that user is Deleted successfully")
    @Description("""
            1. Register User
            2. Log In with User
            3. Delete User
            4. Assert response
            """)
    @Test
    public void deleteUserTest(){
        String name = new DataGenerators().generateRandomName(NAME_MIN_LENGTH, NAME_MAX_LENGTH);
        String email = new DataGenerators().generateRandomEmail(true);
        String password = new DataGenerators().generateRandomPassword(PASSWORD_MIN_LENGTH, PASSWORD_MAX_LENGTH);

        Response createUser = registerUser(new UserBody(name, email, password));
        assertResponseSchema("registration-response-schema.json", createUser);
        assertEquals(HttpStatus.SC_CREATED, createUser.getStatusCode(), "Incorrect status code");

        Response logInUser = SimpleAction.logInUser(new LoginBody(email, password));
        assertResponseSchema("login-response-schema.json", logInUser);
        assertEquals(HttpStatus.SC_OK, logInUser.getStatusCode(), "Incorrect status code");

        String token = logInUser.getBody().jsonPath().getString("data.token");

        Response deleteUserProfile = deleteUserProfile(token);

        BaseResponse response = deleteUserProfile.as(BaseResponse.class);

        assertAll("Verify that user is Deleted successfully",
                () -> assertEquals(SUCCESSFUL_STATUS.getStatus(), response.getStatus(), "Invalid status code"),
                () -> assertEquals(EXPECTED_SUCCESS_TRUE, response.isSuccess(), "Invalid success status"),
                () -> assertEquals( SUCCESSFUL_DELETION_MESSAGE.getMessage(),response.getMessage(),"Invalid message")
        );

        BaseResponse responseAfterSecondDeletion = deleteUserProfile(token).as(BaseResponse.class);
        assertAll("Verify that user not able to Deleted second time",
                () -> assertEquals(UNAUTHORIZED_STATUS.getStatus(), responseAfterSecondDeletion.getStatus(), "Invalid status code"),
                () -> assertEquals(EXPECTED_SUCCESS_FALSE, responseAfterSecondDeletion.isSuccess(), "Invalid success status"),
                () -> assertEquals( UNAUTHORIZED_MESSAGE.getMessage(),responseAfterSecondDeletion.getMessage(),"Invalid message")
        );

    }

    @DisplayName("Verify that user is NOT able to Delete Profile with invalid Token")
    @Description("""
            1. Attempt to Delete user with Invalid Token
            2. Assert response
            """)
    @Test
    public void deleteUserWithInvalidTokenTest(){
        Response responseValidationSchema = deleteUserProfile("wrongToken");
        boolean validationSchema = assertResponseSchema("healthcheck-schema.json", responseValidationSchema);
        BaseResponse response = responseValidationSchema.as(BaseResponse.class);

        assertAll("Verify that user is NOT able to Delete Profile with invalid Token",
                () -> assertTrue(validationSchema, "Invalid response schema"),
                () -> assertEquals(UNAUTHORIZED_STATUS.getStatus(), response.getStatus(), "Invalid status code"),
                () -> assertEquals(EXPECTED_SUCCESS_FALSE, response.isSuccess(), "Invalid success status"),
                () -> assertEquals( UNAUTHORIZED_MESSAGE.getMessage(),response.getMessage(),"Invalid message")
        );

    }
}
