package io.maksym.web.myNotes.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.maksym.web.pages.BasePage;
import io.maksym.web.pages.ForgotPasswordFormPage;
import io.qameta.allure.Step;

public class LoginPage extends BasePage {

    Locator emailField = page.getByLabel("Email address");
    Locator passwordField = page.getByLabel("Password");
    Locator loginBtn = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Login"));
    Locator forgotPasswordLink = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Forgot password"));
    Locator loginTitle = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setLevel(1));

    Locator emailInputLabel = page.locator("//label[@for='email']");
    Locator passwordInputLabel = page.locator("//label[@for='password']");

    public LoginPage(Page page) {
        super(page);
    }
    public void loginBtnShouldBeVisible(){
        loginBtn.waitFor();
        loginBtn.isVisible();
    }
    public void emailFieldShouldBeVisible(){
        emailField.waitFor();
        emailField.isVisible();
    }
    public void passwordFieldShouldBeVisible(){
        passwordField.waitFor();
    }
    public void forgotPasswordLinkShouldBeVisible(){
        forgotPasswordLink.waitFor();
        forgotPasswordLink.isVisible();
    }
    public void loginTitleShouldBeVisible(){
        loginTitle.waitFor();
        loginTitle.isVisible();
    }
    public String getPageTitle(){
        loginTitle.waitFor();
        return loginTitle.innerText();
    }
    @Step("Click on forgot password link")
    public ForgotPasswordFormPage clickOnForgotPasswordLink(){
        forgotPasswordLink.click();
        return new ForgotPasswordFormPage(page);
    }
    public void emailInputLabelShouldBeVisible(){
        emailInputLabel.waitFor();
    }
    public void passwordInputLabelShouldBeVisible(){
        passwordInputLabel.waitFor();
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


    public void loginPageShouldBeOpened(){
        page.waitForURL("**/notes/app/login");
    }

    @Override
    protected String path() {
        return "/notes/app/login";
    }
}
