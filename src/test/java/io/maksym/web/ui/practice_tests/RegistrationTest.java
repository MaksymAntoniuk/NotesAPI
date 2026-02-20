package io.maksym.web.ui.practice_tests;


import io.maksym.web.pages.RegisterPage;
import io.maksym.web.pages.SecurePage;
import io.maksym.web.pages.practice.HomePage;
import io.maksym.web.pages.practice.LoginPage;
import io.maksym.web.records.ui.UiUser;
import io.maksym.web.records.ui.UiUserLogIn;
import io.maksym.web.ui.pages.BaseTest;
import io.maksym.web.util.DataGenerators;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static io.maksym.web.enums.ErrorMessage.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class RegistrationTest extends BaseTest {
        public static Stream<? extends Arguments> registerUserWithNegativeTestProvider() {
                String password = new DataGenerators().generateRandomPassword(6, 10);
                String confirmPassword = password;
                return Stream.of(
                                arguments("with empty [Username]",
                                                USERNAME_MISSED_MESSAGE.getMessage(),
                                                new UiUser("", password, confirmPassword)),

                                arguments("with empty [Password]",
                                                PASSWORD_MISSED.getMessage(),
                                                new UiUser(new DataGenerators().generateRandomName(3, 10), "",
                                                                confirmPassword)),

                                arguments("with empty [Confirm Password]",
                                                PASSWORD_MISSED.getMessage(),
                                                new UiUser(new DataGenerators().generateRandomName(3, 10), password,
                                                                "")),

                                arguments("with empty [All fields]",
                                        ALL_FIELDS_REQUIRED_MESSAGE.getMessage(),
                                                new UiUser("", "", "")),

                                arguments("with [Name] < 4 characters",
                                                USERNAME_SHORT_MESSAGE.getMessage(),
                                                new UiUser("Ma", password,
                                                                confirmPassword)),

                                arguments("with [Name] > 30 characters",
                                                USERNAME_LONG_MESSAGE.getMessage(),
                                                new UiUser("KundeDr. Winfred KossAlton BoscoKundeDr. Winfred KossAlton Bosco", password,
                                                                confirmPassword)));
        }

         @Test
         public void userRegistrationAndLoginTest(){
         UiUser user = new UiUser(new DataGenerators().generateRandomName(1, 30),
         "SuperSecretPassword!", "SuperSecretPassword!");

         HomePage homePage = new HomePage(page()).open();
         RegisterPage registerPage = homePage.goToRegisterPage();
         LoginPage loginPage = registerPage.registerNewUser(user);

         loginPage.loginPageShouldBeOpened();
         loginPage.getFlashAlert().shouldBeVisible();
         loginPage.getFlashAlert().shouldContain("Successfully registered, you can log in now.");

         loginPage.loginAs(new UiUserLogIn(user.username(),
         user.password())).waitUntilLoaded(user.username());

         SecurePage securePage = new SecurePage(page());

         assertAll("User is successfully logged in",
             () -> securePage.securePageShouldBeOpened(),
             () -> securePage.greetingsShouldBeDisplayed(user.username()),
             () -> securePage.isLogoutButtonIsVisible(),
             () -> assertEquals("You logged into a secure area!", securePage.flashAlertText()));
         }

        @MethodSource("registerUserWithNegativeTestProvider" )
        @ParameterizedTest(name = "{0}")
        @DisplayName("Verify that user is NOT able to register successfully")
        public void invalidUserRegistrationAndLoginTest(String testName, String expectedMessage, UiUser user) {

            HomePage homePage = new HomePage(page()).open();
            RegisterPage registerPage = homePage.goToRegisterPage();

            registerPage.tryToRegisterWithInvalidUser(user);

            assertAll(testName,
                    () -> registerPage.registerPageShouldBeOpened(),
                    () -> registerPage.flashAlert().shouldBeVisible(),
                    () -> registerPage.flashAlert().shouldContain(expectedMessage)
            );

        }
}
