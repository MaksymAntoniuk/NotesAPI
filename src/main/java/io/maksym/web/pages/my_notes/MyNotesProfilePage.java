package io.maksym.web.pages.my_notes;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.maksym.web.components.my_notes.AlertToast;
import io.maksym.web.components.my_notes.ConfirmationModal;
import io.maksym.web.enums.UiModalTitle;
import io.maksym.web.pages.BasePage;
import io.maksym.web.records.ui.MyNoteUpdateUser;
import io.qameta.allure.Step;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static io.maksym.web.enums.UiModalTitle.DELETE_ACCOUNT_MODAL_TITLE;

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
    public ConfirmationModal confirmationModal;

    public MyNotesProfilePage(Page page) {
        super(page);
        confirmationModal = new ConfirmationModal(page);
        this.alertToast = new AlertToast(page);
    }

    @Step("Assert Profile page is opened")
    public void assertProfilePageIsOpened(){
        pageTitle.waitFor();
        assert(pageTitle).isVisible();
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

    @Step("Update profile data: Full Name, Phone Number, Company Name")
    public void updateProfile(MyNoteUpdateUser data){
        fillFullNameField(data.getFullName());
        fillPhoneNumberField(data.getPhone());
        fillCompanyNameField(data.getCompany());
        clickUpdateButton();
    }

    @Step("Assert Profile data")
    public void assertProfileData(String email, String fullName, String phoneNumber, String companyName){
        emailField.waitFor();
        assertThat(emailField).hasValue(email);
        assertThat(fullNameField).hasValue(fullName);
        assertThat(phoneNumberField).hasValue(phoneNumber);
        assertThat(companyNameField).hasValue(companyName);

        assertThat(userIdField).isDisabled();
        assertThat(emailField).isDisabled();
    }

    public void assertUserId(String userId){
        userIdField.waitFor();
        assertThat(userIdField).hasValue(userId);
    }

    @Step("Click on Update button")
    public void clickUpdateButton(){
        updateButton.click();
    }

    @Step("Click on Delete Account button")
    public MyNotesLoginPage clickDeleteAccountButton(){
        deleteAccountButton.click();
        ConfirmationModal modal = new ConfirmationModal(page);
        modal.assertModalIsVisible(DELETE_ACCOUNT_MODAL_TITLE.getMessage());
        modal.clickOnDeleteBtn();
        return new MyNotesLoginPage(page);
    }

    @Override
    protected String path() {
        return "";
    }
}
