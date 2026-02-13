package io.maksym.web.myNotes.components;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.qameta.allure.Step;

public class SuccessRegistration {
    Page page;
    Locator successMessage;
    Locator logInLinkOnSuccessPage;

    public SuccessRegistration(Page page) {
        this.page = page;
//        successMessage = page.locator("//*[contains(text(), 'User account created successfully')]");
        successMessage = page.locator(".alert.alert-success").getByText("User account created successfully");

        logInLinkOnSuccessPage = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Click here to Log In"));
    }

    public void successMessageShouldBeVisible(){
        successMessage.waitFor();
        successMessage.isVisible();
    }
    public void logInLinkShouldBeVisible(){
        logInLinkOnSuccessPage.waitFor();
        logInLinkOnSuccessPage.isVisible();
    }
    public String getSuccessMessageText(){
        return successMessage.innerText();
    }

    @Step("Go to Login Page")
    public void goToLoginPage(){
        logInLinkOnSuccessPage.click();
    }
}
