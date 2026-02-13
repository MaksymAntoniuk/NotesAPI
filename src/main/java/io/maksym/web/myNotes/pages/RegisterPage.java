package io.maksym.web.myNotes.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.maksym.web.myNotes.components.AlertToast;
import io.maksym.web.myNotes.components.SuccessRegistration;
import io.maksym.web.pages.BasePage;
import io.qameta.allure.Step;

public class RegisterPage extends BasePage {

    Locator registerBtn = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Register"));
    Locator registerGoogleBtn = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Register with Google"));
    Locator registerLinkedInBtn = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Register with LinkedIn"));
    Locator logInLink = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Log in here!"));
    Locator registerPageTitle = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setLevel(1));

    Locator emailInput = page.getByLabel("Email address");
    Locator nameInput = page.getByLabel("Name");
    Locator passwordInput = page.getByLabel("Password");
    Locator confirmPasswordInput = page.getByTestId("register-confirm-password");

    Locator emailInputLabel = page.locator("//label[@for='email']");
    Locator nameInputLabel = page.locator("//label[@for='name']");
    Locator passwordInputLabel = page.locator("//label[text()='Password']");
    Locator confirmPasswordInputLabel = page.locator("//label[text()='Confirm Password']");

    Locator errorMessage;
    AlertToast alertToast;
    SuccessRegistration successRegistration;



    public RegisterPage(Page page) {
        super(page);
        this.alertToast = new AlertToast(page);
        this.successRegistration = new SuccessRegistration(page);
    }

    public void registerBtnShouldBeVisible(){
        registerBtn.waitFor();
        registerBtn.isVisible();
    }
    public void registerGoogleBtnShouldBeVisible(){
        registerGoogleBtn.waitFor();
        registerGoogleBtn.isVisible();
    }
    public void registerLinkedInBtnShouldBeVisible(){
        registerLinkedInBtn.waitFor();
    }
    public void logInLinkShouldBeVisible(){
        logInLink.waitFor();
    }
    public void registerPageTitleShouldBeVisible(){
        registerPageTitle.waitFor();
        registerPageTitle.isVisible();
    }

    public void emailInputShouldBeVisible(){
        emailInput.waitFor();
        emailInput.isVisible();
    }
    public void nameInputShouldBeVisible(){
        nameInput.waitFor();
        nameInput.isVisible();
    }
    public void passwordInputShouldBeVisible(){
        passwordInput.waitFor();
    }
    public void confirmPasswordInputShouldBeVisible(){
        confirmPasswordInput.waitFor();
    }

    public String getPageTitle(){
        registerPageTitle.waitFor();
        return registerPageTitle.innerText();
    }

    public String getEmailInputLabel(){
        emailInput.waitFor();
        return emailInputLabel.innerText();
    }
    public String getNameInputLabel(){
        nameInput.waitFor();
        return nameInputLabel.innerText();
    }
    public String getPasswordInputLabel(){
        passwordInput.waitFor();
        return passwordInputLabel.innerText();
    }
    public String getConfirmPasswordInputLabel(){
        confirmPasswordInput.waitFor();
        return confirmPasswordInputLabel.innerText();
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
    public RegisterPage clickRegisterBtn(){
        registerBtn.click();
        page.waitForTimeout(2000);
        return this;
    }
    public void fillRegistrationForm(String email, String name, String password, String confirmPassword){
        fillEmail(email);
        fillName(name);
        fillPassword(password);
        fillConfirmPassword(confirmPassword);
    }

    @Step("Verify Toast with error")
    public String getToastErrorMessage(){
        alertToast.shouldBeVisible();
        return alertToast.getAlertText();
    }
    @Step
    public void errorToastShouldBeVisible(){
        alertToast.shouldBeVisible();
    }

    public String getErrorMessage(){
        this.errorMessage = page.locator(".form-group").filter(new Locator.FilterOptions().setHas(this.emailInput)).locator(".invalid-feedback");
        errorMessage.waitFor();
        return errorMessage.innerText();
    }

    public void successRegistrationMessageIsVisible(){
        successRegistration.successMessageShouldBeVisible();
    }
    public String getSuccessRegistrationMessage(){
        return successRegistration.getSuccessMessageText();
    }

    public void linkToLoginPageShouldBeVisible(){
        successRegistration.logInLinkShouldBeVisible();
    }

    @Step("Click on link to login page")
    public LoginPage clickLinkToLoginPage(){
        successRegistration.goToLoginPage();
        return new LoginPage(page);
    }

    @Step("Click on register with Google button")
    public void clickRegisterGoogleBtn(){
        registerGoogleBtn.click();
    }
    @Step("Click on register with LinkedIn button")
    public void clickRegisterLinkedInBtn(){}



    @Override
    protected String path() {
        return "/notes/app/register";
    }
}
