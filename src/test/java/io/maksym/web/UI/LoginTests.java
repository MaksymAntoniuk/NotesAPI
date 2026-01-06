package io.maksym.web.UI;

import io.maksym.web.pages.HomePage;
import io.maksym.web.pages.LoginPage;
import io.maksym.web.pages.RegisterPage;
import io.maksym.web.pages.SecurePage;
import io.maksym.web.records.ui.UiUserLogIn;
import io.maksym.web.util.DataGenerators;
import lombok.extern.apachecommons.CommonsLog;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static io.maksym.web.enums.FlashAlertMessage.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@CommonsLog
public class LoginTests extends io.maksym.web.UI.BaseTest {
    public static Stream<?extends Arguments> loginWithNegativeTestProvider() {
        return Stream.of(
                Arguments.of("with invalid [Username]", INVALID_USERNAME_ALERT.getMessage(), new UiUserLogIn("practice@", "SuperSecretPassword!")),
                Arguments.of("with invalid [Password]", INVALID_PASSWORD_ALERT.getMessage(), new UiUserLogIn("practice", "SuperSecretPassword")),
                Arguments.of("with both invalid [Username] and [Password]", INVALID_USERNAME_ALERT.getMessage() ,new UiUserLogIn(new DataGenerators().generateRandomName(3, 6), new DataGenerators().generateRandomPassword(5, 8)))
        );
    }

    @Test
    @DisplayName("Verify that user is able to login successfully")
    public void verifyUserIsAbleToLogin() {
        HomePage homePage = new HomePage(page).open();

        var loginPage = homePage.goToLogin();
        SecurePage securePage = loginPage.loginAs(new UiUserLogIn("practice", "SuperSecretPassword!"));

        securePage.securePageShouldBeOpened();
        securePage.flashAlert().shouldBeVisible();

        Assertions.assertAll(
                () -> securePage.greetingsShouldBeDisplayed("practice"),
                () -> securePage.flashAlert().shouldBeVisible(),
                () -> securePage.flashAlert().shouldContain(SUCCESSFUL_LOGIN_ALERT.getMessage())
        );

    }
    @Test
    @DisplayName("Verify that user is able to logout successfully")
    public void verifyUserIsAbleToLogout(){
        HomePage homePage = new HomePage(page).open();
        SecurePage securePage = homePage.goToLogin().loginAs(new UiUserLogIn("practice", "SuperSecretPassword!"));
        securePage.securePageShouldBeOpened();
        securePage.isLogoutButtonIsVisible();

        LoginPage logout = securePage.logout();

        Assertions.assertAll(
                () -> logout.flashAlert().shouldBeVisible(),
                () -> logout.flashAlert().shouldContain(LOGOUT_ALERT.getMessage())
        );
    }

    @MethodSource("loginWithNegativeTestProvider")
    @ParameterizedTest(name = "{0}")
    public void verifyUserIsNotAbleToLoginWithInvalidCredentials(String testName, String expectedMessage, UiUserLogIn userLogIn) {
        HomePage homePage = new HomePage(page).open();

        LoginPage loginPage = homePage.goToLogin();
        loginPage.loginWithInvalidUser(userLogIn);

        loginPage.loginPageShouldBeOpened();

        Assertions.assertAll(testName,
                () -> loginPage.loginPageShouldBeOpened(),
                () -> loginPage.flashAlert().shouldBeVisible(),
                () -> loginPage.flashAlert().shouldContain(expectedMessage)
        );
    }
    @Test
    @DisplayName("Verify that user is able to redirect to Registration Page successfully")
    public void verifyUseIsAbleToRedirectToRegistrationPage(){
        HomePage homePage = new HomePage(page).open();
        LoginPage loginPage = homePage.goToLogin();
        RegisterPage registerPage = loginPage.navigateToRegisterPage();

        Assertions.assertAll(
                "Verify user is able to redirect to Registration Page from Login Page",
                () -> registerPage.registerPageShouldBeOpened()
        );

    }
    @Test
    @DisplayName("Verify that user is able to redirect to Home Page successfully")
    public void verifyUserIsAbleToRedirectToHomePage(){
        HomePage homePage = new HomePage(page).open();
        LoginPage loginPage = homePage.goToLogin();
        loginPage.homeLinkShouldBeVisible();
        HomePage returnToHome = loginPage.navigateToHomePage();

        assertEquals("Automation Testing Practice WebSite for QA and Developers", returnToHome.getPageTitle());
    }

}
