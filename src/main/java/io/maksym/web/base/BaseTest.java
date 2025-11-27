package io.maksym.web.base;

import io.maksym.web.Records.LoginBody;
import io.maksym.web.requests.actions.SimpleAction;
import io.maksym.web.config.ApiEndpoints;
import io.maksym.web.dto.Login.LoginResponse;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;

public class BaseTest implements SimpleAction {
    public static String email = "<EMAIL>";
    public static String token = "<TOKEN>";
    public static String name = "<NAME>";
    public static String id = "<ID>";
    static{
        email = "bernini1762597276638@gmail.com";
        LoginResponse loggedInUser = SimpleAction.logInUser(new LoginBody(email, "8jt910m63ozhbnuxsxwj4j4xrxf50"))
                .as(LoginResponse.class);
        id = loggedInUser.getData().getId();
        name = loggedInUser.getData().getName();
        token = loggedInUser.getData().getToken();
    }
    @Before
    public void setup(){
        RestAssured.baseURI = ApiEndpoints.BASE_URL;
    }
    @After
    public void tearDown(){
        RestAssured.reset();
    }

}
