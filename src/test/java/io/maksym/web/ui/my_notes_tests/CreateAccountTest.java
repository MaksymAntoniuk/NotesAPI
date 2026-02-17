package io.maksym.web.ui.my_notes_tests;

import io.maksym.web.pages.my_notes.*;
import io.maksym.web.records.ui.MyNoteRegisterUser;
import io.maksym.web.ui.pages.BaseTest;
import io.maksym.web.pages.HomePage;
import io.maksym.web.test_data.TestUsers;
import io.maksym.web.util.DataGenerators;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.maksym.web.test_data.TestUsers.validUser;

public class CreateAccountTest extends BaseTest {
    @Test
    @DisplayName("Verify that user is able to create account successfully")
    public void varifyCreateAccount(){
        HomePage homePage = new HomePage(page()).open();
        MyNotesWelcomePage myNotesWelcomePage = homePage.goToNotesAppReactPage();
        myNotesWelcomePage.assertWelcomePageIsOpened();

        MyNotesRegisterPage myNotesRegisterPage = myNotesWelcomePage.navigateToRegisterPage();
        myNotesRegisterPage.assertRegisterPageTitleIsVisible();

        String email = new DataGenerators().generateRandomEmail(true);
        String username = new DataGenerators().generateRandomName(3,6);
        String password = new DataGenerators().generateRandomPassword(6,10);
        String confirmPassword = password;

        MyNoteRegisterUser user = new MyNoteRegisterUser(email, username, password, confirmPassword);

        myNotesRegisterPage.fillRegistrationForm(user);
        myNotesRegisterPage.clickRegisterBtn();
        myNotesRegisterPage.successRegistration.assertSuccessMessageIsVisible();

        MyNotesLoginPage myNotesLoginPage = myNotesRegisterPage.clickLinkToLoginPage();
        myNotesLoginPage.assertLogInPageIsOpened();
    }

    @Test
    @DisplayName("Verify that user is NOT able to create account with existing email")
    public void verifyUserIsNotAbleToCreateAccountWithExistingEmail(){
        HomePage homePage = new HomePage(page()).open();
        MyNotesWelcomePage myNotesWelcomePage = homePage.goToNotesAppReactPage();
        myNotesWelcomePage.assertWelcomePageIsOpened();

        MyNotesRegisterPage myNotesRegisterPage = myNotesWelcomePage.navigateToRegisterPage();

        String email = TestUsers.validUser().getEmail();
        String username = new DataGenerators().generateRandomName(3,6);
        String password = new DataGenerators().generateRandomPassword(6,10);
        String confirmPassword = password;

        MyNoteRegisterUser user = new MyNoteRegisterUser(email, username, password, confirmPassword);

        myNotesRegisterPage.fillRegistrationForm(user);
        myNotesRegisterPage.clickRegisterBtn();

        myNotesRegisterPage.assertRegistrationPageIsOpened();
        myNotesRegisterPage.alertToast.assertAlertToastIsVisible();

        myNotesRegisterPage.alertToast.clickOnCloseBtn();
        myNotesRegisterPage.alertToast.assertAlertIsClosed();
    }

    @Test
    @DisplayName("Verify that user is able to login successfully")
    public void verifyUserIsAbleToLogin(){
        HomePage homePage = new HomePage(page()).open();
        MyNotesWelcomePage myNotesWelcomePage = homePage.goToNotesAppReactPage();
        myNotesWelcomePage.assertWelcomePageIsOpened();

        MyNotesLoginPage myNotesLoginPage = myNotesWelcomePage.navigateToLoginPage();
        myNotesLoginPage.assertLogInPageIsOpened();
        MyNotesPage myNotesPage = myNotesLoginPage.fillLoginForm(validUser().getEmail(), validUser().getPassword());
        myNotesPage.assertMyNotesPageIsOpened();
    }
    @Test
    @DisplayName("Verify user is able to Update profile data")
    public void verifyUserIsAbleToUpdateProfile(){
        HomePage homePage = new HomePage(page()).open();
        MyNotesWelcomePage myNotesWelcomePage = homePage.goToNotesAppReactPage();
        myNotesWelcomePage.assertWelcomePageIsOpened();

        MyNotesLoginPage myNotesLoginPage = myNotesWelcomePage.navigateToLoginPage();
        myNotesLoginPage.assertLogInPageIsOpened();
        MyNotesPage myNotesPage = myNotesLoginPage.fillLoginForm(validUser().getEmail(), validUser().getPassword());
        myNotesPage.assertMyNotesPageIsOpened();

        MyNotesProfilePage myNotesProfilePage = myNotesPage.navigationBar.clickOnProfileBtn();
        myNotesProfilePage.assertProfilePageIsOpened();
        myNotesProfilePage.assertUserIdFieldIsVisible();
        myNotesProfilePage.assertUserIdFieldIsDisabled();

        String companyName = new DataGenerators().generateRandomCompany();
        String fullName = new DataGenerators().generateRandomName(3,10);
        String phoneNumber = new DataGenerators().generateRandomPhone();

        myNotesProfilePage.fillCompanyNameField(companyName);
        myNotesProfilePage.fillFullNameField(fullName);
        myNotesProfilePage.fillPhoneNumberField(phoneNumber);
        myNotesProfilePage.clickUpdateButton();

        myNotesProfilePage.alertToast.assertAlertToastIsVisible();
        myNotesProfilePage.alertToast.clickOnCloseBtn();
        myNotesProfilePage.alertToast.assertAlertIsClosed();

        myNotesProfilePage.assertProfileData(validUser().getEmail(), fullName, phoneNumber, companyName);
    }

    @Test
    @DisplayName("Verify user is able to delete account")
    public void verifyUserIsAbleToDeleteAccount(){
        HomePage homePage = new HomePage(page()).open();
        MyNotesWelcomePage myNotesWelcomePage = homePage.goToNotesAppReactPage();
        myNotesWelcomePage.assertWelcomePageIsOpened();

        MyNotesRegisterPage myNotesRegisterPage = myNotesWelcomePage.navigateToRegisterPage();
        myNotesRegisterPage.assertRegisterPageTitleIsVisible();

        String email = new DataGenerators().generateRandomEmail(true);
        String username = new DataGenerators().generateRandomName(3,6);
        String password = new DataGenerators().generateRandomPassword(6,10);
        String confirmPassword = password;

        MyNoteRegisterUser user = new MyNoteRegisterUser(email, username, password, confirmPassword);

        myNotesRegisterPage.fillRegistrationForm(user);
        myNotesRegisterPage.clickRegisterBtn();
        myNotesRegisterPage.successRegistration.assertSuccessMessageIsVisible();

        MyNotesLoginPage myNotesLoginPage = myNotesRegisterPage.clickLinkToLoginPage();
        myNotesLoginPage.assertLogInPageIsOpened();
        MyNotesPage myNotesPage = myNotesLoginPage.fillLoginForm(email, password);

        MyNotesProfilePage myNotesProfilePage = myNotesPage.navigationBar.clickOnProfileBtn();
        myNotesProfilePage.assertProfilePageIsOpened();
        myNotesProfilePage.clickDeleteAccountButton();

        myNotesProfilePage.deleteAccountModal.assertModalIsVisible();
        MyNotesLoginPage loginPage = myNotesProfilePage.deleteAccountModal.clickOnDeleteBtn();
        loginPage.alertToast.assertAlertToastIsVisible();
    }

}
