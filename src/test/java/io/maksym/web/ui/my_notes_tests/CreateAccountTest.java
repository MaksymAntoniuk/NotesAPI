package io.maksym.web.ui.my_notes_tests;

import io.maksym.web.pages.my_notes.*;
import io.maksym.web.pages.practice.HomePage;
import io.maksym.web.records.ui.MyNoteLoginUser;
import io.maksym.web.records.ui.MyNoteRegisterUser;
import io.maksym.web.ui.BaseTest;
import io.maksym.web.test_data.TestUsers;
import io.maksym.web.util.DataGenerators;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.maksym.web.enums.UiErrorMessage.ACCOUNT_EXISTS_MESSAGE;

@Epic("My Notes App")
@DisplayName("Verify user is able to create account")
@Severity(io.qameta.allure.SeverityLevel.CRITICAL)
public class CreateAccountTest extends BaseTest {
    @Test
    @DisplayName("Verify that user is able to create account successfully")
    @Description("""
    1. Navigate to https://practice.expandtesting.com/
    2. Click on the "Notes App | React" card
    3. Navigate to the Registration page
    4. Fill in the email, username, and password fields
    5. Click "Register" and verify the success message
    6. Log in with the new credentials
    7. Navigate to the Profile page and verify the User ID matches the registration response
    8. Log out and delete the test user profile
    """)
    public void varifyCreateAccountTest(){
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

        String userId = myNotesRegisterPage.registerNewUser(user);
        myNotesRegisterPage.successRegistration.assertSuccessMessageIsVisible();

        MyNotesLoginPage myNotesLoginPage = myNotesRegisterPage.clickLinkToLoginPage();
        myNotesLoginPage.assertLogInPageIsOpened();

        MyNotesPage myNotesPage = myNotesLoginPage.logInWithUser(loginUser);
        myNotesPage.assertMyNotesPageIsOpened();

        MyNotesProfilePage myNotesProfilePage = myNotesPage.navigationBar.clickOnProfileBtn();
        myNotesProfilePage.assertProfilePageIsOpened();
        myNotesProfilePage.assertUserId(userId);

        MyNotesWelcomePage welcomePage = myNotesPage.navigationBar.clickOnLogOutBtn();
        welcomePage.assertWelcomePageIsOpened();

        welcomePage.goToLoginPage().logInWithUser(loginUser).navigationBar.clickOnProfileBtn().deleteUserProfile();
    }

    @Test
    @DisplayName("Verify that user is NOT able to create account with existing email")
    @Description("""
    1. Navigate to https://practice.expandtesting.com/
    2. Click on the "Notes App | React" card
    3. Navigate to the Registration page
    4. Fill the form using an email address that is already registered
    5. Click the "Register" button
    6. Verify that the user remains on the Registration page
    7. Verify that an error message "An account has already been registered with that email address" is displayed
    8. Close the alert and verify it is closed
    """)
    public void verifyUserIsNotAbleToCreateAccountWithExistingEmailTest(){
        String email = TestUsers.validUser().getEmail();
        String username = new DataGenerators().generateRandomName(4,6);
        String password = new DataGenerators().generateRandomPassword(6,10);
        String confirmPassword = password;

        MyNoteRegisterUser user = new MyNoteRegisterUser(email, username, password, confirmPassword);

        HomePage homePage = new HomePage(page()).open();
        MyNotesWelcomePage myNotesWelcomePage = homePage.goToMyNotesWelcomePage();
        myNotesWelcomePage.assertWelcomePageIsOpened();

        MyNotesRegisterPage myNotesRegisterPage = myNotesWelcomePage.goToRegisterPage();

        myNotesRegisterPage.registerNewUser(user);

        myNotesRegisterPage.assertRegistrationPageIsOpened();
        myNotesRegisterPage.alertToast.assertAlertToastIsVisible(ACCOUNT_EXISTS_MESSAGE.getMessage());

        myNotesRegisterPage.alertToast.clickOnCloseBtn();
        myNotesRegisterPage.alertToast.assertAlertIsClosed();
    }
}
