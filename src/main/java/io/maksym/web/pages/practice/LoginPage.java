package io.maksym.web.pages.practice;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.maksym.web.components.practice.FlashAlert;
import io.maksym.web.pages.BasePage;
import io.maksym.web.records.ui.UiUserLogIn;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static io.maksym.web.config.ApiEndpoints.BASE_URL_UI;

public class LoginPage extends BasePage {
    private final FlashAlert flashAlert;
    private final Locator username = page.locator("//input[@name='username']");
    private final Locator password = page.locator("//input[@name='password']");
    private final Locator hereLink = page.locator("//a[@href='/register']");
    private final Locator homeLink = page.getByRole(
            AriaRole.LINK,
            new Page.GetByRoleOptions().setName("Home")
    );

    private final Locator submit = page.getByRole(AriaRole.BUTTON,
            new Page.GetByRoleOptions().setName("Login"));


    public LoginPage(Page page) {
        super(page);
        this.flashAlert = new FlashAlert(page.locator("#flash"));
    }
    public FlashAlert getFlashAlert(){
        return new FlashAlert(page.locator("#flash"));
    }

    @Override
    protected String path() {
        return "/login";
    }

    public void fillUserName(String username){
        this.username.fill(username);
    }

    public void fillPassword(String password){
        this.password.fill(password);
    }

    public void loginClick(){
        submit.click();
    }

    public String flashAlertText(){
        assert(page.locator("#flash").isVisible());
        return flashAlert.text();
    }

    public io.maksym.web.pages.SecurePage loginAs(UiUserLogIn user){
        fillUserName(user.username());
        fillPassword(user.password());
        loginClick();
        page.waitForURL("**/secure");
        return new io.maksym.web.pages.SecurePage(page);
    }

    public LoginPage loginWithInvalidUser(UiUserLogIn user){
        fillUserName(user.username());
        fillPassword(user.password());
        loginClick();
        return new LoginPage(page);
    }

    public io.maksym.web.pages.RegisterPage navigateToRegisterPage(){
        hereLink.click();
        return new io.maksym.web.pages.RegisterPage(page);
    }

    public LoginPage loginPageShouldBeOpened(){
        page.waitForTimeout(1000);
        assertThat(page).hasURL(BASE_URL_UI + "/login");
        assertThat(
                page.getByRole(
                        AriaRole.HEADING,
                        new Page.GetByRoleOptions().setLevel(1)
                )
        ).containsText("Test Login page");

        return this;
    }
    public void homeLinkShouldBeVisible(){
        assertThat(homeLink).isVisible();
    }
    public HomePage navigateToHomePage(){
        homeLink.click();
        return new HomePage(page);
    }

    public void isHereLinkIsVisible(){
        assertThat(hereLink).isVisible();
    }

}
