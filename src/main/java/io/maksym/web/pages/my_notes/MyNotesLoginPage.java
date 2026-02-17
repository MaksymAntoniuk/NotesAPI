package io.maksym.web.pages.my_notes;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.maksym.web.components.my_notes.AlertToast;
import io.maksym.web.pages.BasePage;
import io.maksym.web.pages.ForgotPasswordFormPage;
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
        alertToast = new AlertToast(page, "Your account has been deleted. You should create a new account to continue.");
    }

    @Step("Assert Login button is visible")
    public void assertLoginButtonIsVisible(){
        loginBtn.waitFor();
        loginBtn.isVisible();
    }
    @Step("Assert Email field is visible")
    public void assertEmailFieldIsVisible(){
        emailField.waitFor();
        emailField.isVisible();
    }
    @Step("Assert Password field is visible")
    public void assertPasswordFieldIsVisible(){
        passwordField.waitFor();
    }
    @Step("Assert Forgot password link is visible")
    public void assertForgotPasswordLinkIsVisible(){
        forgotPasswordLink.waitFor();
        forgotPasswordLink.isVisible();
    }
    @Step("Assert Login page title is visible")
    public void assertLoginPageTitleIsVisible(){
        loginTitle.waitFor();
        loginTitle.isVisible();
    }
    @Step("Click on forgot password link")
    public ForgotPasswordFormPage clickOnForgotPasswordLink(){
        forgotPasswordLink.click();
        return new ForgotPasswordFormPage(page);
    }

    @Step("Enter email")
    public void enterEmail(String email){
        emailField.fill(email);
    }
    @Step("Enter password")
    public void enterPassword(String password){
        passwordField.fill(password);
    }
    @Step("Click on login button")
    public void clickLoginBtn(){
        loginBtn.click();
    }

    public MyNotesPage fillLoginForm(String email, String password){
        enterEmail(email);
        enterPassword(password);
        page.waitForTimeout(2000);
        clickLoginBtn();
        return new MyNotesPage(page);
    }

    @Step("Assert Log In page is opened")
    public void assertLogInPageIsOpened(){
        page.waitForURL("**/notes/app/login");
    }

    @Override
    protected String path() {
        return "/notes/app/login";
    }
}
