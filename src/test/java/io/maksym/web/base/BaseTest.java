package io.maksym.web.base;

import io.maksym.web.records.LoginBody;
import io.maksym.web.records.UserBody;
import io.maksym.web.requests.actions.SimpleAction;
import io.maksym.web.config.ApiEndpoints;
import io.maksym.web.dto.Login.LoginResponse;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BaseTest implements SimpleAction {
    public static String email = "<EMAIL>";
    public static String token = "<TOKEN>";
    public static String name = "<NAME>";
    public static String id = "<ID>";

    public static List<String> notesToDelete = Collections.synchronizedList(new ArrayList<>());
    public static List<UserBody> usersToDelete = Collections.synchronizedList(new ArrayList<>());

    static{
        email = "bernini1762597276638@gmail.com";
        LoginResponse loggedInUser = SimpleAction.logInUser(new LoginBody(email, "8jt910m63ozhbnuxsxwj4j4xrxf50"))
                .as(LoginResponse.class);
        id = loggedInUser.getData().getId();
        name = loggedInUser.getData().getName();
        token = loggedInUser.getData().getToken();
    }
    @BeforeEach
    public void setup(){
        RestAssured.baseURI = ApiEndpoints.BASE_URL;
    }
    @AfterAll
    public static void tearDown(){
        System.out.println("Cleaning up...");

        BaseTest action = new BaseTest();
        try {
            notesToDelete.forEach(noteId ->{
                action.deleteNoteById(token, noteId);
            });
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        notesToDelete.clear();

        usersToDelete.forEach(user -> {
            try {
                LoginResponse response = SimpleAction.logInUser(new LoginBody(user.email(),user.password())).as(LoginResponse.class);
                new BaseTest().deleteUserProfile(response.getData().getToken());
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        });
        usersToDelete.clear();

        RestAssured.reset();
    }

    public void registerCreatedUser(UserBody user){
        usersToDelete.add(user);
    }
    public void registerCreatedNote(String noteId){
        notesToDelete.add(noteId);
    }

}
