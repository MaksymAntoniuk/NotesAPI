package io.maksym.web.ui.my_notes_tests;

import io.maksym.web.pages.my_notes.*;
import io.maksym.web.pages.practice.HomePage;
import io.maksym.web.records.ui.MyNoteLoginUser;
import io.maksym.web.records.ui.MyNoteRegisterUser;
import io.maksym.web.ui.pages.BaseTest;
import io.maksym.web.test_data.TestUsers;
import io.maksym.web.util.DataGenerators;
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

        myNotesRegisterPage.registerNewUser(user);
        myNotesRegisterPage.successRegistration.assertSuccessMessageIsVisible();

        MyNotesLoginPage myNotesLoginPage = myNotesRegisterPage.clickLinkToLoginPage();
        myNotesLoginPage.assertLogInPageIsOpened();

        MyNotesPage myNotesPage = myNotesLoginPage.logInWithUser(loginUser);
        myNotesPage.assertMyNotesPageIsOpened();

        MyNotesWelcomePage welcomePage = myNotesPage.navigationBar.clickOnLogOutBtn();
        welcomePage.assertWelcomePageIsOpened();
    }

    @Test
    @DisplayName("Verify that user is NOT able to create account with existing email")
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
        myNotesRegisterPage.clickRegisterBtn();

        myNotesRegisterPage.assertRegistrationPageIsOpened();
        myNotesRegisterPage.alertToast.assertAlertToastIsVisible(ACCOUNT_EXISTS_MESSAGE.getMessage());

        myNotesRegisterPage.alertToast.clickOnCloseBtn();
        myNotesRegisterPage.alertToast.assertAlertIsClosed();
    }
}
