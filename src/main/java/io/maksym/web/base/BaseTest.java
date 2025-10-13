package io.maksym.web.base;

import io.maksym.web.config.ApiEndpoints;
import io.maksym.web.dto.User.User;
import io.maksym.web.actions.SimpleAction;
import io.maksym.web.util.DataGenerators;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;

import static io.maksym.web.validation.ValidationConstants.*;
import static io.maksym.web.validation.ValidationConstants.NAME_MAX_LENGTH;

public abstract class BaseTest {
    protected String email = "<EMAIL>";
    protected String password = "<PASSWORD>";
    @BeforeEach
    public void setup(){
        RestAssured.baseURI = ApiEndpoints.BASE_URL;

        SimpleAction simpleAction = new SimpleAction();
        String passwordCred = new DataGenerators().generateRandomPassword(PASSWORD_MIN_LENGTH, PASSWORD_MAX_LENGTH);
        var createdUser = simpleAction.createUser(new User(new DataGenerators().generateRandomName(NAME_MIN_LENGTH, NAME_MAX_LENGTH), new DataGenerators().generateRandomEmail(true), passwordCred));
        email = createdUser.getData().getEmail();
        password = passwordCred;
    }

}
