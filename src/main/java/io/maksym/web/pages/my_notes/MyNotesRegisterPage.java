package io.maksym.web.pages.my_notes;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Response;
import com.microsoft.playwright.options.AriaRole;
import io.maksym.web.components.my_notes.AlertToast;
import io.maksym.web.components.my_notes.SuccessRegistration;
import io.maksym.web.pages.BasePage;
import io.maksym.web.records.ui.MyNoteRegisterUser;
import io.qameta.allure.Step;

public class MyNotesRegisterPage extends BasePage {

    Locator registerBtn = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Register"));
    Locator registerGoogleBtn = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Register with Google"));
    Locator registerLinkedInBtn = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Register with LinkedIn"));
    Locator logInLink = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Log in here!"));
    Locator registerPageTitle = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Register"));

    Locator emailInput = page.getByLabel("Email address");
    Locator nameInput = page.getByLabel("Name");
    Locator passwordInput = page.getByLabel("Password");
    Locator confirmPasswordInput = page.getByTestId("register-confirm-password");

    Locator errorMessage;
    public AlertToast alertToast;

    public SuccessRegistration successRegistration;

    public MyNotesRegisterPage(Page page) {
        super(page);
        this.alertToast = new AlertToast(page);
        this.successRegistration = new SuccessRegistration(page);
    }

    @Step("Enter email")
    public void fillEmail(String email){
        emailInput.waitFor();
        emailInput.fill(email);
    }

    @Step("Enter name")
    public void fillName(String name){
        nameInput.waitFor();
        nameInput.fill(name);
    }

    @Step("Enter password")
    public void fillPassword(String password){
        passwordInput.waitFor();
        passwordInput.fill(password);
    }

    @Step("Enter confirm password")
    public void fillConfirmPassword(String confirmPassword){
        confirmPasswordInput.waitFor();
        confirmPasswordInput.click();
        page.keyboard().type(confirmPassword);
        confirmPasswordInput.fill(confirmPassword);
    }

    @Step("Click on register button")
    public MyNotesLoginPage clickRegisterBtn(){
        registerBtn.click();
        return new MyNotesLoginPage(page);
    }

    @Step("Submit registration form")
    public String registerNewUser(MyNoteRegisterUser user){
        fillEmail(user.email());
        fillName(user.username());
        fillPassword(user.password());
        fillConfirmPassword(user.password());

        com.microsoft.playwright.Response response = page.waitForResponse(
                res -> res.url().contains("https://practice.expandtesting.com/notes/api/users/register") && res.request().method().equals("POST"),
                () -> {
                    clickRegisterBtn();
                }
        );

        String body = response.text();
        JsonObject json = new Gson().fromJson(body, JsonObject.class);

        if(response.status() == 201 && json.getAsJsonObject("data").get("id") != null){
            return json.getAsJsonObject("data").get("id").getAsString();
        }

        return null;
    }

    @Step("Click on link to login page")
    public MyNotesLoginPage clickLinkToLoginPage(){
        successRegistration.goToLoginPage();
        return new MyNotesLoginPage(page);
    }

    @Step("Click on register with Google button")
    public void clickRegisterGoogleBtn(){
        registerGoogleBtn.click();
    }

    @Step("Click on register with LinkedIn button")
    public void clickRegisterLinkedInBtn(){
        registerLinkedInBtn.click();
    }

    @Step("Assert Registration page is opened")
    public MyNotesRegisterPage assertRegistrationPageIsOpened(){
        registerPageTitle.waitFor();
        assert(registerPageTitle).isVisible();
        page.waitForURL("**/notes/app/register");
        return this;
    }

    @Override
    protected String path() {
        return "/notes/app/register";
    }
}
