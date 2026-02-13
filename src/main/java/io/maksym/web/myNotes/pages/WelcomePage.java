package io.maksym.web.myNotes.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.maksym.web.pages.BasePage;
import io.qameta.allure.Step;

public class WelcomePage extends BasePage {
    Locator loginBtn = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Login"));
    Locator createAccountLink = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Create an account"));
    Locator forgotPasswordLink = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Forgot your password?"));
    Locator googleAccountLink = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Google Account"));
    Locator pageTitle = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setLevel(1));

    public WelcomePage(Page page) {
        super(page);
    }

    @Step("Welcome page opens")
    public void welcomePageShouldBeOpened(){
        pageTitle.waitFor();
        pageTitle.isVisible();
        page.waitForURL("**/notes/app");
    }

    public void loginBtnShouldBeVisible(){
        loginBtn.waitFor();
        loginBtn.isVisible();
    }
    public void createAccountBtnShouldBeVisible(){
        createAccountLink.waitFor();
        createAccountLink.isVisible();
    }
    public void forgotPasswordLinkShouldBeVisible(){
        forgotPasswordLink.waitFor();
    }

    public String getPageTitle(){
        pageTitle.waitFor();
        return pageTitle.innerText();
    }

    @Step("Navigate to Register page")
    public RegisterPage navigateToRegisterPage(){
        createAccountLink.click();
        page.waitForURL("**/notes/app/register");
        return new RegisterPage(page);
    }



    @Override
    protected String path() {
        return "/notes/app";
    }
}
