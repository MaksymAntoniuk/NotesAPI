package io.maksym.web.pages.my_notes;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
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
        this.alertToast = new AlertToast(page, "An account already exists with the same email address");
        this.successRegistration = new SuccessRegistration(page);
    }

    @Step("Assert Register button is visible")
    public boolean assertRegisterBtnShouldBeVisible(){
        registerBtn.waitFor();
        return registerBtn.isVisible();
    }
    @Step("Assert Register with Google button is visible")
    public boolean assertRegisterGoogleBtnShouldBeVisible(){
        registerGoogleBtn.waitFor();
        return registerGoogleBtn.isVisible();
    }
    @Step("Assert Register with LinkedIn button is visible")
    public boolean assertRegisterLinkedInBtnShouldBeVisible(){
        registerLinkedInBtn.waitFor();
        return registerLinkedInBtn.isVisible();
    }
    @Step("Assert Log In link is visible")
    public boolean assertLogInLinkIsVisible(){
        logInLink.waitFor();
        return logInLink.isVisible();
    }
    @Step("Assert Register page title is visible")
    public void assertRegisterPageTitleIsVisible(){
        registerPageTitle.waitFor();
        registerPageTitle.isVisible();
    }

    @Step("Assert email input is visible")
    public void assertEmailInputShouldBeVisible(){
        emailInput.waitFor();
        emailInput.isVisible();
    }
    @Step("Assert name input is visible")
    public void assertNameInputIsVisible(){
        nameInput.waitFor();
        nameInput.isVisible();
    }
    @Step("Assert password input is visible")
    public void assertPasswordInputIsVisible(){
        passwordInput.waitFor();
        passwordInput.isVisible();
    }
    @Step("Assert confirm password input is visible")
    public void assertConfirmPasswordInputIsVisible(){
        confirmPasswordInput.waitFor();
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
    public void clickRegisterBtn(){
        registerBtn.click();
        page.waitForTimeout(2000);
    }
    @Step("Submit registration form")
    public void fillRegistrationForm(MyNoteRegisterUser user){
        fillEmail(user.email());
        fillName(user.username());
        fillPassword(user.password());
        fillConfirmPassword(user.password());
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

    public MyNotesRegisterPage assertRegistrationPageIsOpened(){
        assertRegisterPageTitleIsVisible();
        page.waitForURL("**/notes/app/register");
        return this;
    }

    @Override
    protected String path() {
        return "/notes/app/register";
    }
}
