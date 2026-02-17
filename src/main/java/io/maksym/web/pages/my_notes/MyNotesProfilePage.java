package io.maksym.web.pages.my_notes;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.maksym.web.components.my_notes.AlertToast;
import io.maksym.web.components.my_notes.DeleteAccountModal;
import io.maksym.web.pages.BasePage;
import io.qameta.allure.Step;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class MyNotesProfilePage extends BasePage {
    Locator pageTitle = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Profile settings"));

    Locator userIdField = page.getByTestId("user-id");
    Locator emailField = page.getByTestId("user-email");
    Locator fullNameField = page.getByTestId("user-name");
    Locator phoneNumberField = page.getByTestId("user-phone");
    Locator companyNameField = page.getByTestId("user-company");

    Locator updateButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Update profile"));
    Locator deleteAccountButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Delete Account"));

    public AlertToast alertToast;
    public DeleteAccountModal deleteAccountModal;

    public MyNotesProfilePage(Page page) {
        super(page);
        deleteAccountModal = new DeleteAccountModal(page);
        this.alertToast = new AlertToast(page, "Profile updated successful");
    }

    @Step("Assert Profile page is opened")
    public void assertProfilePageIsOpened(){
        pageTitle.waitFor();
        pageTitle.isVisible();
    }
    @Step("Assert User ID field is visible")
    public void assertUserIdFieldIsVisible(){
        userIdField.waitFor();
        userIdField.isVisible();
    }
    @Step("Assert Email field is visible")
    public void assertEmailFieldIsVisible(){
        emailField.waitFor();
        emailField.isVisible();
    }
    @Step("Assert Full Name field is visible")
    public void assertFullNameFieldIsVisible(){
        fullNameField.waitFor();
        fullNameField.isVisible();
    }
    @Step("Assert Phone Number field is visible")
    public void assertPhoneNumberFieldIsVisible(){
        phoneNumberField.waitFor();
        phoneNumberField.isVisible();
    }
    @Step("Assert Company Name field is visible")
    public void assertCompanyNameFieldIsVisible(){
        companyNameField.waitFor();
        phoneNumberField.isVisible();
    }
    @Step("Assert Update button is visible")
    public void assertUpdateButtonIsVisible(){
        updateButton.waitFor();
        updateButton.isVisible();
    }
    @Step("Assert Delete Account button is visible")
    public void assertDeleteAccountButtonIsVisible(){
        deleteAccountButton.waitFor();
        deleteAccountButton.isVisible();
    }
    @Step("Fill Full Name field")
    public void fillFullNameField(String fullName){
        fullNameField.fill(fullName);
    }
    @Step("Fill Email field")
    public void fillPhoneNumberField(String phoneNumber){
        phoneNumberField.fill(phoneNumber);
    }
    @Step("Fill Company Name field")
    public void fillCompanyNameField(String companyName){
        companyNameField.fill(companyName);
    }
    @Step("Assert User ID field is disabled")
    public void assertUserIdFieldIsDisabled(){
        assertThat(userIdField).isDisabled();
    }
    @Step("Assert Email field is disabled")
    public void assertEmailFieldIsDisabled(){
        assertThat(emailField).isDisabled();
    }
    @Step("Assert Profile data")
    public void assertProfileData(String email, String fullName, String phoneNumber, String companyName){
        emailField.waitFor();
        assertThat(emailField).hasValue(email);
        assertThat(fullNameField).hasValue(fullName);
        assertThat(phoneNumberField).hasValue(phoneNumber);
        assertThat(companyNameField).hasValue(companyName);
    }

    @Step("Click on Update button")
    public void clickUpdateButton(){
        updateButton.click();
    }
    @Step("Click on Delete Account button")
    public void clickDeleteAccountButton(){
        deleteAccountButton.click();
    }



    @Override
    protected String path() {
        return "";
    }
}
