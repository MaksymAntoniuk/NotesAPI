package io.maksym.web.pages.my_notes;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.maksym.web.components.my_notes.AlertToast;
import io.maksym.web.pages.BasePage;
import io.maksym.web.pages.ForgotPasswordFormPage;
import io.maksym.web.records.ui.MyNoteLoginUser;
import io.qameta.allure.Step;

public class MyNotesLoginPage extends BasePage {

    Locator emailField = page.getByLabel("Email address");
    Locator passwordField = page.getByLabel("Password");
    Locator loginBtn = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Login"));
    Locator forgotPasswordLink = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Forgot password"));
    Locator loginTitle = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Login"));

    public AlertToast alertToast;

    public MyNotesLoginPage(Page page) {
        super(page);
        alertToast = new AlertToast(page);
    }

    @Step("Click on forgot password link")
    public ForgotPasswordFormPage clickOnForgotPasswordLink(){
        forgotPasswordLink.waitFor();
        forgotPasswordLink.click();
        return new ForgotPasswordFormPage(page);
    }

    @Step("Enter email")
    public void enterEmail(String email){
        emailField.waitFor();
        emailField.fill(email);
    }

    @Step("Enter password")
    public void enterPassword(String password){
        passwordField.waitFor();
        passwordField.fill(password);
    }

    @Step("Click on login button")
    public void clickLoginBtn(){
        loginBtn.waitFor();
        loginBtn.click();
    }

    @Step("Log in with user {0}")
    public MyNotesPage logInWithUser(MyNoteLoginUser user){
        enterEmail(user.getEmail());
        enterPassword(user.getPassword());
        clickLoginBtn();
        return new MyNotesPage(page);
    }

    @Step("Assert Log In page is opened")
    public void assertLogInPageIsOpened(){
        loginTitle.waitFor();
        page.waitForURL("**/notes/app/login");
    }

    @Override
    protected String path() {
        return "/notes/app/login";
    }
}
