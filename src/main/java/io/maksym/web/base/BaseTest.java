package io.maksym.web.base;

import io.maksym.web.Records.LoginBody;
import io.maksym.web.requests.actions.SimpleAction;
import io.maksym.web.config.ApiEndpoints;
import io.maksym.web.dto.Login.LoginResponse;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

public class BaseTest implements SimpleAction {
    protected static String email = "<EMAIL>";
    public static String token = "<TOKEN>";
    protected static String name = "<NAME>";
    protected static String id = "<ID>";

    @BeforeAll
    public static void setup(){
        RestAssured.baseURI = ApiEndpoints.BASE_URL;

        email = "bernini1762597276638@gmail.com";
        LoginResponse loggedInUser = SimpleAction.logInUser(new LoginBody(email, "8jt910m63ozhbnuxsxwj4j4xrxf50"))
                .as(LoginResponse.class);
        id = loggedInUser.getData().getId();
        name = loggedInUser.getData().getName();
        token = loggedInUser.getData().getToken();
    }

    @AfterEach
    public void tearDown(){
        RestAssured.reset();
    }

}
