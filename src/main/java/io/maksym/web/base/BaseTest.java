package io.maksym.web.base;

import io.maksym.web.Records.LoginBody;
import io.maksym.web.Records.UserBody;
import io.maksym.web.actions.SimpleAction;
import io.maksym.web.config.ApiEndpoints;
import io.maksym.web.dto.Login.LoginResponse;
import io.maksym.web.dto.Registration.RegistrationSuccResponse.RegistrationSuccessfulResponse;
import io.maksym.web.util.DataGenerators;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static io.maksym.web.config.ApiEndpoints.ENDPOINT_CREATE_USER;
import static io.maksym.web.config.ApiEndpoints.ENDPOINT_LOG_IN;
import static io.maksym.web.util.Constants.*;

public class BaseTest extends SimpleAction {
    protected String email = "<EMAIL>";
    protected String password = "<PASSWORD>";
    protected String token = "<TOKEN>";
    protected String name = "<NAME>";
    protected String id = "<ID>";

    @BeforeEach
    public void setup(){
        RestAssured.baseURI = ApiEndpoints.BASE_URL;
        String passwordCred = new DataGenerators().generateRandomPassword(PASSWORD_MIN_LENGTH, PASSWORD_MAX_LENGTH);
        RegistrationSuccessfulResponse createdUser = postRequest(ENDPOINT_CREATE_USER, new UserBody(new DataGenerators().generateRandomName(NAME_MIN_LENGTH, NAME_MAX_LENGTH), new DataGenerators().generateRandomEmail(true), passwordCred)).as(RegistrationSuccessfulResponse.class);

        email = createdUser.getData().getEmail();
        password = passwordCred;

        LoginResponse loggedInUser = postRequest( ENDPOINT_LOG_IN, new LoginBody(email, password)).as(LoginResponse.class);

        token = loggedInUser.getData().getToken();
        id = loggedInUser.getData().getId();
        name = loggedInUser.getData().getName();
    }

    @AfterEach
    public void tearDown(){
        RestAssured.reset();
    }

}
