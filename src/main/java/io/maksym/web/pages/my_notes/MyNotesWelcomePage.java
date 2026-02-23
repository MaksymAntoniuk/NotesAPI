package io.maksym.web.pages.my_notes;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.maksym.web.pages.BasePage;
import io.qameta.allure.Step;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class MyNotesWelcomePage extends BasePage {
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
        page.waitForURL("**/notes/app");
        assertThat(pageTitle).isVisible();
    }

    @Step("Navigate to Register page")
    public MyNotesRegisterPage goToRegisterPage(){
        createAccountLink.click();
        page.waitForURL("**/notes/app/register");
        return new MyNotesRegisterPage(page);
    }

    @Step("Navigate to Login page")
    public MyNotesLoginPage goToLoginPage(){
        loginLink.click();
        return new MyNotesLoginPage(page);
    }

    @Override
    protected String path() {
        return "/notes/app";
    }
}
