package io.maksym.web.ui.my_notes_tests;


import io.maksym.web.pages.my_notes.*;
import io.maksym.web.pages.practice.HomePage;
import io.maksym.web.records.ui.MyNoteLoginUser;
import io.maksym.web.records.ui.MyNoteRegisterUser;
import io.maksym.web.records.ui.MyNoteUpdateUser;
import io.maksym.web.ui.BaseTest;
import io.maksym.web.util.DataGenerators;
import io.qameta.allure.Epic;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.maksym.web.enums.UiErrorMessage.*;
import static io.maksym.web.test_data.TestUsers.validUser;

@Epic("My Notes App")
@DisplayName("Verify user is able to update profile data")
public class ProfileAccountTest extends BaseTest {
    @Test
    @DisplayName("Verify user is able to Update profile data")
    public void verifyUserIsAbleToUpdateProfileTest(){
        String companyName = new DataGenerators().generateRandomCompany();
        String fullName = new DataGenerators().generateRandomName(4,10);
        String phoneNumber = new DataGenerators().generateRandomPhone();

        MyNoteUpdateUser updateUser = new MyNoteUpdateUser(fullName, phoneNumber, companyName);

        String email = new DataGenerators().generateRandomEmail(true);
        String username = new DataGenerators().generateRandomName(4,6);
        String password = new DataGenerators().generateRandomPassword(6,10);
        String confirmPassword = password;

        MyNoteRegisterUser user = new MyNoteRegisterUser(email, username, password, confirmPassword);
        MyNoteLoginUser loginUser = new MyNoteLoginUser(email, password);

        HomePage homePage = new HomePage(page()).open();
        MyNotesWelcomePage myNotesWelcomePage = homePage.goToMyNotesWelcomePage();
        myNotesWelcomePage.assertWelcomePageIsOpened();

        MyNotesRegisterPage myNotesRegisterPage = myNotesWelcomePage.goToRegisterPage();
        myNotesRegisterPage.assertRegistrationPageIsOpened();

        myNotesRegisterPage.registerNewUser(user);
        myNotesRegisterPage.successRegistration.assertSuccessMessageIsVisible();

        MyNotesLoginPage myNotesLoginPage = myNotesRegisterPage.clickLinkToLoginPage();
        myNotesLoginPage.assertLogInPageIsOpened();

        MyNotesPage myNotesPage = myNotesLoginPage.logInWithUser(loginUser);
        myNotesPage.assertMyNotesPageIsOpened();

        MyNotesProfilePage myNotesProfilePage = myNotesPage.navigationBar.clickOnProfileBtn();
        myNotesProfilePage.assertProfilePageIsOpened();

        myNotesProfilePage.updateProfile(updateUser);

        myNotesProfilePage.alertToast.assertAlertToastIsVisible(SUCCESSFUL_PROFILE_UPDATE_MESSAGE.getMessage());
        myNotesProfilePage.alertToast.clickOnCloseBtn();
        myNotesProfilePage.alertToast.assertAlertIsClosed();

        myNotesProfilePage.assertProfileData(user.email(), updateUser.getFullName(),
                updateUser.getPhone(), updateUser.getCompany());

        myNotesProfilePage.clickDeleteAccountButton().assertLogInPageIsOpened();
    }

    @Test
    @DisplayName("Verify user is able to delete account")
    public void verifyUserIsAbleToDeleteAccountTest(){
        String email = new DataGenerators().generateRandomEmail(true);
        String username = new DataGenerators().generateRandomName(4,6);
        String password = new DataGenerators().generateRandomPassword(6,10);
        String confirmPassword = password;

        MyNoteRegisterUser user = new MyNoteRegisterUser(email, username, password, confirmPassword);
        MyNoteLoginUser loginUser = new MyNoteLoginUser(email, password);

        HomePage homePage = new HomePage(page()).open();
        MyNotesWelcomePage myNotesWelcomePage = homePage.goToMyNotesWelcomePage();
        myNotesWelcomePage.assertWelcomePageIsOpened();

        MyNotesRegisterPage myNotesRegisterPage = myNotesWelcomePage.goToRegisterPage();
        myNotesRegisterPage.assertRegistrationPageIsOpened();

        myNotesRegisterPage.registerNewUser(user);
        myNotesRegisterPage.successRegistration.assertSuccessMessageIsVisible();

        MyNotesLoginPage myNotesLoginPage = myNotesRegisterPage.clickLinkToLoginPage();
        myNotesLoginPage.assertLogInPageIsOpened();
        MyNotesPage myNotesPage = myNotesLoginPage.logInWithUser(loginUser);

        MyNotesProfilePage myNotesProfilePage = myNotesPage.navigationBar.clickOnProfileBtn();
        myNotesProfilePage.assertProfilePageIsOpened();
       MyNotesLoginPage loginPage = myNotesProfilePage.clickDeleteAccountButton();

        loginPage.alertToast.assertAlertToastIsVisible(SUCCESSFUL_ACCOUNT_DELETION_MESSAGE.getMessage());
        loginPage.assertLogInPageIsOpened();
        loginPage.logInWithUser(loginUser);
        loginPage.alertToast.assertAlertToastIsVisible(INVALID_LOGIN_MESSAGE.getMessage());
    }
}
