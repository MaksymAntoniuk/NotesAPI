package io.maksym.web.pages.my_notes;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.maksym.web.components.my_notes.AlertToast;
import io.maksym.web.components.my_notes.ConfirmationModal;
import io.maksym.web.components.my_notes.NavigationBar;
import io.maksym.web.pages.BasePage;
import io.maksym.web.records.ui.MyNoteUpdateUser;
import io.qameta.allure.Step;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static io.maksym.web.enums.UiErrorMessage.SUCCESSFUL_PROFILE_UPDATE_MESSAGE;
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

    Locator changePasswordTab = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Change password"));
    Locator currentPasswordField = page.getByTestId("current-password");
    Locator newPasswordField = page.getByTestId("new-password");
    Locator confirmPasswordField = page.getByTestId("confirm-password");
    Locator updatePasswordButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Update password"));

    public AlertToast alertToast;
    public ConfirmationModal confirmationModal;
    public NavigationBar navigationBar;

    public MyNotesProfilePage(Page page) {
        super(page);
        confirmationModal = new ConfirmationModal(page);
        this.alertToast = new AlertToast(page);
        this.navigationBar = new NavigationBar(page);
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
    public MyNotesLoginPage deleteUserProfile(){
        deleteAccountButton.click();
        ConfirmationModal modal = new ConfirmationModal(page);
        modal.assertModalIsVisible(DELETE_ACCOUNT_MODAL_TITLE.getMessage());
        modal.clickOnDeleteBtn();
        return new MyNotesLoginPage(page);
    }

    @Step("Click on Change password tab")
    public void clickChangePasswordTab(){
        changePasswordTab.click();
    }

    @Step("Fill Current Password field")
    public void fillCurrentPasswordField(String password){
        currentPasswordField.fill(password);
    }

    @Step("Fill New Password field")
    public void fillNewPasswordField(String password){
        newPasswordField.fill(password);
    }

    @Step("Fill Confirm Password field")
    public void fillConfirmPasswordField(String password){
        confirmPasswordField.fill(password);
    }

    @Step("Click on Update password button")
    public void clickUpdatePasswordButton(){
        updatePasswordButton.click();
    }

    @Step("Update password")
    public void updatePassword(String currentPassword, String newPassword){
        clickChangePasswordTab();
        fillCurrentPasswordField(currentPassword);
        fillNewPasswordField(newPassword);
        fillConfirmPasswordField(newPassword);
        clickUpdatePasswordButton();
    }

    @Step("Assert Password update message")
    public void assertPasswordUpdatedMessage(){
        alertToast.assertAlertToastIsVisible("The password was successfully updated");
    }

    @Step("Assert successful Profile update message")
    public void assertSuccessfulProfileUpdateMessage(){
        alertToast.assertAlertToastIsVisible(SUCCESSFUL_PROFILE_UPDATE_MESSAGE.getMessage());
    }

    @Override
    protected String path() {
        return "";
    }
}
