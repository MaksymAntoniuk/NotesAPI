package io.maksym.web.ReactNotesApp;

import io.maksym.web.myNotes.pages.LoginPage;
import io.maksym.web.myNotes.pages.MyNotesPage;
import io.maksym.web.myNotes.pages.RegisterPage;
import io.maksym.web.myNotes.pages.WelcomePage;
import io.maksym.web.pages.BaseTest;
import io.maksym.web.pages.HomePage;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreateAccountTest extends BaseTest {

    @Test
    public void CreateAccountTest(){
        HomePage homePage = new HomePage(page()).open();
        WelcomePage welcomePage = homePage.goToNotesAppReactPage();
        assertAll("Verify that user is able to create account successfully",
                () -> welcomePage.welcomePageShouldBeOpened(),
                () -> assertEquals("Welcome to Notes App", welcomePage.getPageTitle())
        );
        RegisterPage registerPage = welcomePage.navigateToRegisterPage();

        assertAll("",
                () -> registerPage.registerPageTitleShouldBeVisible(),
                () -> assertEquals("Register", registerPage.getPageTitle()),
                () -> registerPage.registerBtnShouldBeVisible(),
                () -> registerPage.emailInputShouldBeVisible(),
                () -> registerPage.nameInputShouldBeVisible(),
                () -> registerPage.passwordInputShouldBeVisible(),
                () -> registerPage.confirmPasswordInputShouldBeVisible(),
                () -> assertEquals("Email address",registerPage.getEmailInputLabel()),
                () -> assertEquals("Name", registerPage.getNameInputLabel()),
                () -> assertEquals("Password", registerPage.getPasswordInputLabel()),
                () -> assertEquals("Confirm Password", registerPage.getConfirmPasswordInputLabel())
        );
        String email = "email" + System.currentTimeMillis() +"@gmail.com";
        String password = "Test123456";

        registerPage.fillRegistrationForm(email, "Test User", "Test123456", "Test123456");
//        -------------------------------------------------
//        RegisterPage invalidRegistration = registerPage.clickRegisterBtn();
//        invalidRegistration.errorToastShouldBeVisible();
//        assertEquals("An account already exists with the same email address", invalidRegistration.getToastErrorMessage());

//        assertEquals("Email address is required", invalidRegistration.getErrorMessage());
        //---------------------------------------------------------------
        RegisterPage successfulPage = registerPage.clickRegisterBtn();
        successfulPage.successRegistrationMessageIsVisible();
        assertEquals("User account created successfully", successfulPage.getSuccessRegistrationMessage());
        successfulPage.linkToLoginPageShouldBeVisible();

        LoginPage loginPage = successfulPage.clickLinkToLoginPage();
        loginPage.loginPageShouldBeOpened();
        assertEquals("Login", loginPage.getPageTitle());
        loginPage.emailFieldShouldBeVisible();
        loginPage.passwordFieldShouldBeVisible();
        loginPage.passwordInputLabelShouldBeVisible();
        loginPage.emailInputLabelShouldBeVisible();

        MyNotesPage myNotesPage = loginPage.fillLoginForm(email, password);
        myNotesPage.homeLogoBtnShouldBeVisible();
        myNotesPage.profileButtonShouldBeVisible();
        myNotesPage.logOutBtnShouldBeVisible();

        WelcomePage logOutPage = myNotesPage.clickOnLogOutBtn();
        logOutPage.welcomePageShouldBeOpened();
        assertEquals("Welcome to Notes App", logOutPage.getPageTitle());




    }

}
