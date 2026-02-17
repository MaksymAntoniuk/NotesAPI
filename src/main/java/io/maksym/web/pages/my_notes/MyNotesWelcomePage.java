package io.maksym.web.pages.my_notes;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.maksym.web.pages.BasePage;
import io.qameta.allure.Step;

public class MyNotesWelcomePage extends BasePage {
//    Locator loginBtn = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Login"));
    Locator loginLink = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Login"));

    Locator createAccountLink = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Create an account"));
    Locator forgotPasswordLink = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Forgot your password?"));
    Locator googleAccountLink = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Google Account"));
    Locator pageTitle = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Welcome to Notes App"));

    public MyNotesWelcomePage(Page page) {
        super(page);
    }

    @Step("Welcome page opens")
    public void assertWelcomePageIsOpened(){
        pageTitle.isVisible();
        page.waitForURL("**/notes/app");
    }
    @Step("Assert Log In button is visible")
    public void assertLoginBtnShouldBeVisible(){
        loginLink.waitFor();
        loginLink.isVisible();
    }
    @Step("Assert Create Account button is visible")
    public void assertCreateAccountLinkShouldBeVisible(){
        createAccountLink.waitFor();
        createAccountLink.isVisible();

    }
    @Step("Assert Forgot Password button is visible")
    public void assertForgotPasswordLinkShouldBeVisible(){
        forgotPasswordLink.waitFor();
        forgotPasswordLink.isVisible();
    }
    @Step("Assert Google Account button is visible")
    public void assertGoogleAccountLinkShouldBeVisible(){
        googleAccountLink.waitFor();
        googleAccountLink.isVisible();
    }
    @Step("Navigate to Register page")
    public MyNotesRegisterPage navigateToRegisterPage(){
        createAccountLink.click();
        page.waitForURL("**/notes/app/register");
        return new MyNotesRegisterPage(page);
    }
    @Step("Navigate to Login page")
    public MyNotesLoginPage navigateToLoginPage(){
        loginLink.click();
        return new MyNotesLoginPage(page);
    }

    @Override
    protected String path() {
        return "/notes/app";
    }
}
