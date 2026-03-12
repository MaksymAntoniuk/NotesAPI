package io.maksym.web.ui.my_notes_tests;

import io.maksym.web.pages.my_notes.*;
import io.maksym.web.pages.practice.HomePage;
import io.maksym.web.records.ui.MyNoteLoginUser;
import io.maksym.web.records.ui.MyNoteRegisterUser;
import io.maksym.web.records.ui.MyNoteUpdateUser;
import io.maksym.web.ui.BaseTest;
import io.maksym.web.util.DataGenerators;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Epic("My Notes App")
@DisplayName("Verify user is able to update profile data")
public class ProfileAccountTest extends BaseTest {
    @Test
    @DisplayName("Verify user is able to Update profile data")
    @Description("""
    1. Navigate to https://practice.expandtesting.com/
    2. Click on the "Notes App | React" card
    3. Navigate to the Registration page and create a new account
    4. Log in and navigate to the Profile page via the navigation bar
    5. Update the profile information with a new Full Name, Phone Number, and Company
    6. Verify that a success toast message is displayed and can be dismissed
    7. Verify that the updated profile data matches the submitted values
    8. Delete the user profile and verify redirection to Login page
    """)
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

        myNotesProfilePage.assertSuccessfulProfileUpdateMessage();
        myNotesProfilePage.alertToast.clickOnCloseBtn();
        myNotesProfilePage.alertToast.assertAlertIsClosed();

        myNotesProfilePage.assertProfileData(user.email(), updateUser.getFullName(),
                updateUser.getPhone(), updateUser.getCompany());

        myNotesProfilePage.deleteUserProfile().assertLogInPageIsOpened();
    }

    @Test
    @DisplayName("Verify user is able to change Password")
    @Description("""
    1. Navigate to https://practice.expandtesting.com/
    2. Click on the "Notes App | React" card
    3. Navigate to the Registration page and create a new account
    4. Log in and navigate to the Profile page
    5. Update the account password using the current password and a new random password
    6. Verify that the password update success message is displayed
    7. Log out of the application
    8. Attempt to log in with the OLD password and verify that "Invalid credentials" error appears
    9. Log in with the NEW password and verify successful access to the dashboard
    10. Delete the user profile
    """)
    public void verifyUserIsAbleToChangePasswordTest(){
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
        String newPassword = new DataGenerators().generateRandomPassword(6,10);
        myNotesProfilePage.updatePassword(password, newPassword);
        myNotesProfilePage.assertPasswordUpdatedMessage();

        MyNotesLoginPage loginPage = myNotesProfilePage.navigationBar.clickOnLogOutBtn().goToLoginPage()
                        .logInWithInvalidUser(loginUser);
        loginPage.assertInvalidCredentialsMessage();

        MyNotesPage notesPage = loginPage.logInWithUser(new MyNoteLoginUser(email, newPassword));
        notesPage.navigationBar.clickOnProfileBtn().deleteUserProfile();
    }
}
