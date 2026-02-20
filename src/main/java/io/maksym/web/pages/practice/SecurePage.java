package io.maksym.web.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;
import io.maksym.web.components.practice.FlashAlert;
import io.maksym.web.pages.practice.LoginPage;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static io.maksym.web.config.ApiEndpoints.BASE_URL_UI;

public class SecurePage extends BasePage{
    private final FlashAlert flashAlert;
    public static final String GREETING_MESSAGE = "//h3[@id='username' and normalize-space()='Hi, %s!']";
    private final Locator logout = page.getByRole(AriaRole.LINK,
            new Page.GetByRoleOptions().setName("Logout"));

    public SecurePage(Page page) {
        super(page);
        flashAlert = new FlashAlert(page.locator("#flash"));
    }

    @Override
    protected String path() {
        return "/secure";
    }

    public SecurePage waitUntilLoaded(String username){
        page.locator(String.format(GREETING_MESSAGE, username.toLowerCase()))
                .waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        return this;
    }

//    public Locator greeting(String username){
//        return page.locator(String.format(GREETING_MESSAGE, username.toLowerCase()));
//    }
    public String flashAlertText(){
        assert(page.locator("#flash").isVisible());
        return flashAlert.text();
    }

    public void securePageShouldBeOpened(){
        page.waitForURL("**/secure");
        assertThat(page).hasURL(BASE_URL_UI + "/secure");
    }

    public void greetingsShouldBeDisplayed(String username){
        Locator greeting = page.locator(String.format(GREETING_MESSAGE, username.toLowerCase()));
        assertThat(greeting).isVisible();
    }

    public void isLogoutButtonIsVisible(){
        assertThat(logout).isVisible();
    }

    public LoginPage logout(){
        logout.click();
        return new LoginPage(page);
    }
}
