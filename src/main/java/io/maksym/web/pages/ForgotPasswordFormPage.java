package io.maksym.web.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.maksym.web.components.FlashAlert;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static io.maksym.web.config.ApiEndpoints.BASE_URL_UI;

public class ForgotPasswordFormPage extends BasePage{
    private Locator pageHeading = page.locator("h1");
    private Locator emailInput = page.locator("#email");
    private Locator retrievePasswordBtn = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Retrieve password"));
    private Locator errorMessage = page.locator(".invalid-feedback");
    private Locator backToHome = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Home"));

    public ForgotPasswordFormPage(Page page) {
        super(page);
    }
    public FlashAlert getFlashAlert(){
        return new FlashAlert(page.locator("#confirmation-alert"));
    }
    public String getFlashAlertText(){
        getFlashAlert().shouldBeVisible();
        page.waitForTimeout(2000);
        return getFlashAlert().text();
    }
    public String getPageHeading(){
        assertThat(pageHeading).isVisible();
        return pageHeading.innerText();
    }

    public ForgotPasswordFormPage formShouldBeOpened(){
        assertThat(page).hasURL(BASE_URL_UI + "/forgot-password");
        emailInputIsVisible();
        pageHeadingIsVisible();
        return this;
    }

    public void enterEmail(String email){
        emailInput.fill(email);
    }

    public ForgotPasswordFormPage clickRetrievePassword(){
        retrievePasswordBtn.click();
        return this;
    }

    public String getErrorMessage(){
        assertThat(errorMessage).isVisible();
        return errorMessage.innerText();
    }

    public void isRetrievePasswordButtonVisible(){
        assertThat(retrievePasswordBtn).isVisible();
    }
    public void emailInputIsVisible(){
        assertThat(emailInput).isVisible();
    }
    public void pageHeadingIsVisible(){
         assertThat(pageHeading).isVisible();
    }

    public HomePage clickBackLink() {
        backToHome.click();
        return new HomePage(page);
    }


    @Override
    protected String path() {
        return "";
    }
}
