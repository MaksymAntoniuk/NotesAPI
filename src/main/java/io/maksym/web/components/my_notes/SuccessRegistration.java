package io.maksym.web.components.my_notes;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.maksym.web.pages.my_notes.MyNotesLoginPage;
import io.qameta.allure.Step;

public class SuccessRegistration {
    Page page;
    Locator successMessage;
    Locator logInLinkOnSuccessPage;

    public SuccessRegistration(Page page) {
        this.page = page;
        successMessage = page.locator(".alert.alert-success").getByText("User account created successfully");
        logInLinkOnSuccessPage = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Click here to Log In"));
    }

    @Step("Assert Success Message is visible")
    public void assertSuccessMessageIsVisible(){
        successMessage.waitFor();
        successMessage.isVisible();
    }
    @Step("Assert Log In link on Success Page is visible")
    public void assertLogInLinkOnSuccessPageIsVisible(){
        logInLinkOnSuccessPage.waitFor();
        logInLinkOnSuccessPage.isVisible();
    }
    @Step("Go to Login Page")
    public MyNotesLoginPage goToLoginPage(){
        logInLinkOnSuccessPage.click();
        return new MyNotesLoginPage(page);
    }
}
