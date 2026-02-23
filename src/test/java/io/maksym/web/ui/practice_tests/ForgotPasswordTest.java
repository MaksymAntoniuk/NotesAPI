package io.maksym.web.ui.practice_tests;

import io.maksym.web.pages.practice.HomePage;
import io.maksym.web.ui.BaseTest;
import io.maksym.web.pages.ForgotPasswordFormPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class ForgotPasswordTest extends BaseTest {
    public static Stream<? extends Arguments> resetPasswordWithInvalidEmail() {
        return Stream.of(
                arguments("with invalid [Email]", "test@mail."),
                arguments("with empty [Email]", "")
        );
    }
    @Test
    public void verifyUserIsAbleToResetPassword(){

        HomePage homePage = new HomePage(page()).open();
        ForgotPasswordFormPage passwordFormPage = homePage.goToForgotPasswordForm();


        passwordFormPage.enterEmail("test@gmail.com");
        passwordFormPage.formShouldBeOpened();
        passwordFormPage.isRetrievePasswordButtonVisible();

        var successPage = passwordFormPage.clickRetrievePassword();

        assertAll(
                "Verify User is able to reset password successfully",
                () -> assertEquals("Password reset page for Automation Testing Practice", passwordFormPage.getPageHeading()),
                () -> passwordFormPage.getFlashAlert().shouldBeVisible(),
                () -> assertEquals("An e-mail has been sent to you which explains how to reset your password.", passwordFormPage.getFlashAlertText(), "Incorrect message"),
                () -> assertEquals("Password reset page for Automation Testing Practice", successPage.getPageHeading())
        );
    }

    @MethodSource("resetPasswordWithInvalidEmail")
    @ParameterizedTest(name = "{0}")
    @DisplayName("Verify that user is NOT able to reset password with invalid email")
    public void verifyUserIsNotAbleToResetPasswordWithInvalidEmail(String testName, String email){
        HomePage homePage = new HomePage(page()).open();
        ForgotPasswordFormPage forgotPasswordFormPage = homePage.goToForgotPasswordForm();
        assertAll(testName,
                () -> forgotPasswordFormPage.formShouldBeOpened(),
                () -> forgotPasswordFormPage.enterEmail(email),
                () -> forgotPasswordFormPage.clickRetrievePassword(),
                () -> forgotPasswordFormPage.formShouldBeOpened(),
                () -> assertEquals("Please enter a valid email address.", forgotPasswordFormPage.getErrorMessage())
        );
    }

    @Test
    @DisplayName("Verify that user is able to redirect to Home Page successfully")
    public void verifyUserIsAbleToRedirectToHome(){
        HomePage homePage = new HomePage(page()).open();
        ForgotPasswordFormPage forgotPasswordPage = homePage.goToForgotPasswordForm();

        forgotPasswordPage.formShouldBeOpened();
        HomePage homeRedirection = forgotPasswordPage.clickBackLink();
        assertEquals("Automation Testing Practice WebSite for QA and Developers", homeRedirection.getPageTitle());
    }

}
