package io.maksym.web.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class HomePage extends BasePage {
    public HomePage(Page page) {
        super(page);
    }

    private final Locator webInputLink = page.getByRole(
            AriaRole.LINK,
            new Page.GetByRoleOptions().setName("Web inputs"));

    private final Locator loginPageLink = page.getByRole(
            AriaRole.LINK,
            new Page.GetByRoleOptions().setName("Test Login Page"));

    private final Locator registrationLink = page.getByRole(
            AriaRole.LINK,
            new Page.GetByRoleOptions().setName("Test Register Page"));
    private final Locator mainTitle = page.locator("#main-title");
    private final Locator scrollbarsLink = page.getByRole(
            AriaRole.LINK,
            new Page.GetByRoleOptions().setName("Scrollbars"));

    private final Locator forgotPasswordForm = page.getByRole(
            AriaRole.LINK,
            new Page.GetByRoleOptions().setName("Forgot password form"));

    private final Locator webInputs = page.getByRole(AriaRole.LINK,
            new Page.GetByRoleOptions().setName("Web inputs"));

    private final Locator locatorPageLink = page.getByRole(AriaRole.LINK,
            new Page.GetByRoleOptions().setName("Locators Page"));

    public LoginPage goToLogin() {
        loginPageLink.click();
        return new LoginPage(page);
    }

    public LocatorsPage goToLocatorsPage(){
        locatorPageLink.click();
        return new LocatorsPage(page);
    }


    public RegisterPage goToRegisterPage(){
        registrationLink.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(5000));

        registrationLink.click();

        assertThat(
                page.getByRole(
                        AriaRole.HEADING,
                        new Page.GetByRoleOptions().setLevel(1)
                )
        ).containsText("Test Register page for Automation Testing Practice");

        return new RegisterPage(page);
    }

    public ScrollPage goToScrollPage(){
        scrollbarsLink.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(5000));
        scrollbarsLink.click();
        return new ScrollPage(page);
    }

    public String getPageTitle(){
        return mainTitle.innerText();
    }
    public ForgotPasswordFormPage goToForgotPasswordForm(){
        forgotPasswordForm.click();
        return new ForgotPasswordFormPage(page);
    }
    public WebInputsPage goToWebInputsPage(){
        webInputs.click();
        return new WebInputsPage(page);
    }

    @Override
    protected String path() {
        return "/";
    }
}
